package br.com.alura.leilao.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.alura.leilao.databinding.FormUsuarioBinding
import br.com.alura.leilao.model.Usuario

class NovoUsuarioDialog(
    private val listener: UsuarioCriadoListener,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            val binding = FormUsuarioBinding.inflate(layoutInflater)
            val campoNome = binding.formUsuarioNome.editText

            setTitle("Novo usuário")
            setView(binding.root)
            setPositiveButton("Adicionar") { _, _ ->
                listener.criado(Usuario(nome = campoNome?.text.toString()))
            }
        }.create()
    }

    interface UsuarioCriadoListener {
        fun criado(usuario: Usuario)
    }
}

