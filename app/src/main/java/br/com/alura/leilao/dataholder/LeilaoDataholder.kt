package br.com.alura.leilao.dataholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.repository.LeilaoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeilaoDataholder(
    private val ioScope: CoroutineScope,
    client: LeilaoWebClient,
) : ViewModel() {

    private val repository = LeilaoRepository(client)
    private val _leiloes = MutableStateFlow(listOf<Leilao>())
    val leiloes = _leiloes.asStateFlow()

    fun buscaLeiloes(listener: ErroCarregamentoListener) {
        repository.todos(object : RespostaListener<List<Leilao>> {
            override fun sucesso(resposta: List<Leilao>?) {
                ioScope.launch {
                    _leiloes.emit(resposta ?: emptyList())
                }
            }

            override fun falha(mensagem: String?) {
                listener.aoFalhar(mensagem)
            }
        })
    }

    fun propoe(
        lance: Lance,
        leilao: Leilao,
        lanceListener: LanceProcessadoListener,
        exceptionListener: PropoeExceptionListener,
        falhaEnvioListener: FalhaEnvioListener
    ) {
        try {
            leilao.propoe(lance)
        } catch (e: RuntimeException) {
            when (e) {
                is QuantidadeMaximaDeLancesException,
                is ValorMenorQueOAnteriorException,
                is UsuarioDeuLancesSeguidosException -> {
                    exceptionListener.handleException(e)
                }

                else -> throw e
            }
        }
        ioScope.launch {
            repository.propoe(lance, leilao.id, object : RespostaListener<Void> {
                override fun sucesso(resposta: Void?) {
                    lanceListener.processado(leilao)
                }

                override fun falha(mensagem: String?) {
                    falhaEnvioListener.reportaFalha()
                }
            })
        }
    }

    interface FalhaEnvioListener {
        fun reportaFalha()
    }

    interface PropoeExceptionListener {
        fun handleException(e: RuntimeException)
    }

    interface LanceProcessadoListener {
        fun processado(leilao: Leilao)
    }

    interface ErroCarregamentoListener {
        fun aoFalhar(mensagem: String?)
    }

    class Factory(
        private val ioScope: CoroutineScope,
        private val client: LeilaoWebClient,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            modelClass.getConstructor(CoroutineScope::class.java, LeilaoWebClient::class.java)
                .newInstance(ioScope, client)
    }
}