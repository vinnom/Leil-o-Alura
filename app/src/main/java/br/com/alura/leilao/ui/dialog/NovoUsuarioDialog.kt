package br.com.alura.leilao.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.alura.leilao.databinding.FormUsuarioBinding
import br.com.alura.leilao.model.Usuario

class NovoUsuarioDialog(
    private val listener: UsuarioCriadoListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            AlertDialog.Builder(activity).apply {
                val binding = FormUsuarioBinding.inflate(activity.layoutInflater)
                val campoNome = binding.formUsuarioNome.editText

                setTitle("Novo usuário")
                setView(binding.root)
                setPositiveButton("Adicionar") { dialog, which ->
                    listener.criado(Usuario(nome = campoNome?.text.toString()))
                }
            }.create()
        } ?: throw IllegalArgumentException("Activity cannot be null")
    }

    interface UsuarioCriadoListener{
        fun criado(usuario: Usuario)
    }

}