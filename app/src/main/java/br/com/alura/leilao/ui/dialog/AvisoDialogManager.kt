package br.com.alura.leilao.ui.dialog

import android.app.AlertDialog
import android.content.Context

class AvisoDialogManager(private val context: Context) {

    companion object {
        private const val MENSAGEM_PADRAO_BOTAO_POSITIVO = "Ok"
        private const val MENSAGEM_AVISO_JA_DEU_CINCO_LANCES = "Usuário não pode dar mais de cinco lances no mesmo leilão"
        private const val MENSAGEM_AVISO_LANCE_SEGUIDO_MESMO_USUARIO = "O mesmo usuário do último lance não pode propror novos lances"
        private const val MENSAGEM_AVISO_LANCE_MENOR_QUE_ULTIMO_LANCE = "Lance precisa ser maior que o último lance"
        private const val MENSAGEM_AVISO_FALHA_NO_ENVIO_DO_LANCE = "Não foi possível enviar Lance"
        private const val MENSAGEM_AVISO_VALOR_INVALIDO = "Valor inválido"
    }

    fun mostraToastFalhaNoEnvio() {
        mostraDialog(MENSAGEM_AVISO_FALHA_NO_ENVIO_DO_LANCE)
    }

    fun mostraAvisoUsuarioJaDeuCincoLances() {
        mostraDialog(MENSAGEM_AVISO_JA_DEU_CINCO_LANCES)
    }

    fun mostraAvisoLanceSeguidoDoMesmoUsuario() {
        mostraDialog(MENSAGEM_AVISO_LANCE_SEGUIDO_MESMO_USUARIO)
    }

    fun mostraAvisoLanceMenorQueUltimoLance() {
        mostraDialog(MENSAGEM_AVISO_LANCE_MENOR_QUE_ULTIMO_LANCE)
    }

    fun mostraAvisoValorInvalido() {
        mostraDialog(MENSAGEM_AVISO_VALOR_INVALIDO)
    }

    private fun mostraDialog(mensagem: String) {
        AlertDialog.Builder(context)
            .setMessage(mensagem)
            .setPositiveButton(MENSAGEM_PADRAO_BOTAO_POSITIVO, null)
            .show()
    }
}
