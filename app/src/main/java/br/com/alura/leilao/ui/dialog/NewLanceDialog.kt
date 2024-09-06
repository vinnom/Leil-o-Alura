package br.com.alura.leilao.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import br.com.alura.leilao.databinding.FormLanceBinding
import br.com.alura.leilao.dataholder.UsuarioDataholder
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.activity.ListaUsuarioFragment
import kotlinx.coroutines.launch

class NewLanceDialog(
    private val containerId: Int,
    private val usuarioDataholder: UsuarioDataholder,
    private val lanceCriadoListener: LanceCriadoListener,
) : DialogFragment() {

    private val formLanceBinding by lazy { FormLanceBinding.inflate(layoutInflater) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return viewLifecycleOwner.lifecycleScope.launch {
            val usuarios = usuarioDataholder.todos()
            when {
                usuarios.isEmpty() -> criaSemUsuariosCadastradosDialog()
                else -> criaNovoLanceDialog(usuarios)
            }
        }.let { Dialog(requireContext()) } // only for satisfying the return type
    }

    private fun criaNovoLanceDialog(usuarios: List<Usuario>) =
            AlertDialog.Builder(requireContext()).apply {
                formLanceBinding.formLanceUsuario.adapter =
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            usuarios
                        )
                setTitle("Novo lance")
                setView(formLanceBinding.root)
                setPositiveButton("Propor") { _, _ ->
                    val itemSelecionado = formLanceBinding.formLanceUsuario.selectedItem
                    val valorEmTexto = formLanceBinding.formLanceValor.editText?.text.toString()
                    try {
                        Lance(
                            itemSelecionado as Usuario,
                            valorEmTexto.toDouble()
                        ).let { lance ->
                            lanceCriadoListener.lanceCriado(lance)
                        }
                    } catch (_: NumberFormatException) {
                        setTitle("")
                        setView(null)
                        setMessage("Valor inválido")
                        setPositiveButton("Ok", null)
                    }
                }
                setNegativeButton("Cancelar", null)
            }.create()

    private fun criaSemUsuariosCadastradosDialog() = AlertDialog.Builder(requireContext()).apply {
        setTitle("Usuários não encontrados")
        setMessage("Não existe usuários cadastrados! Cadastre um usuário para propor o lance.")
        setPositiveButton("Cadastrar usuário") { _, _ ->
            parentFragmentManager.run {
                beginTransaction().run {
                    replace(containerId, ListaUsuarioFragment(), "ListaUsuario")
                    commit()
                }
            }
        }
    }.create()

    interface LanceCriadoListener {
        fun lanceCriado(lance: Lance)
    }
}
