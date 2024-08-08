package br.com.alura.leilao.api

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.dialog.AvisoDialogManager

class EnviadorDeLance(
    private val client: LeilaoWebClient,
    private val listener: LanceProcessadoListener,
    private val dialogManager: AvisoDialogManager
) {

    fun envia(leilao: Leilao, lance: Lance) {
        try {
            leilao.propoe(lance)
            client.propoe(lance, leilao.id, object : RespostaListener<Void> {
                override fun sucesso(resposta: Void?) {
                    listener.processado(leilao)
                }

                override fun falha(mensagem: String) {
                    dialogManager.mostraToastFalhaNoEnvio()
                }
            })
        } catch (exception: ValorMenorQueOAnteriorException) {
            dialogManager.mostraAvisoLanceMenorQueUltimoLance()
        } catch (exception: UsuarioDeuLancesSeguidosException) {
            dialogManager.mostraAvisoLanceSeguidoDoMesmoUsuario()
        } catch (exception: QuantidadeMaximaDeLancesException) {
            dialogManager.mostraAvisoUsuarioJaDeuCincoLances()
        }
    }

    interface LanceProcessadoListener {
        fun processado(leilao: Leilao)
    }
}