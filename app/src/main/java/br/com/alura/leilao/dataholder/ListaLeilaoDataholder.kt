package br.com.alura.leilao.dataholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.leilao.repository.ListaLeiloesRepository
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.model.Leilao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaLeilaoDataholder(
    private val ioScope: CoroutineScope,
    client: LeilaoWebClient
) : ViewModel() {

    private val repository = ListaLeiloesRepository(client)
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

    interface ErroCarregamentoListener {
        fun aoFalhar(mensagem: String?)
    }

    class Factory(
        private val ioScope: CoroutineScope,
        private val client: LeilaoWebClient
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            modelClass.getConstructor(CoroutineScope::class.java, LeilaoWebClient::class.java)
                .newInstance(ioScope, client)
    }
}