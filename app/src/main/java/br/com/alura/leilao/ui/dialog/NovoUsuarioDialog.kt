package br.com.alura.leilao.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import br.com.alura.leilao.databinding.FormUsuarioBinding
import br.com.alura.leilao.model.Usuario

class NovoUsuarioDialog(
    private val context: Context,
    private val listener: UsuarioCriadoListener
) {

    companion object {
        private const val TITULO = "Novo usuário"
        private const val DESCRICAO_BOTAO_POSITIVO = "Adicionar"
    }

    fun mostra() {
        val binding = FormUsuarioBinding.inflate(LayoutInflater.from(context))
        val campoNome = binding.formUsuarioNome.editText

        AlertDialog.Builder(context)
            .setTitle(TITULO)
            .setView(binding.root)
            .setPositiveButton(DESCRICAO_BOTAO_POSITIVO, criaNovoUsuarioListener(campoNome))
            .show()
    }

    private fun criaNovoUsuarioListener(campoNome: EditText?) =
        DialogInterface.OnClickListener { _, _ ->
            val nome = campoNome?.text.toString()
            val novoUsuario = Usuario(nome = nome)
            listener.criado(novoUsuario)
        }

    interface UsuarioCriadoListener {
        fun criado(usuario: Usuario)
    }
}

