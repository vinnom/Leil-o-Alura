package br.com.alura.leilao.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.alura.leilao.LeilaoAluraApp
import br.com.alura.leilao.databinding.FragmentListaUsuarioBinding
import br.com.alura.leilao.dataholder.ListaUsuarioDataholder
import br.com.alura.leilao.dataholder.ListaUsuarioDataholder.Factory
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.dialog.NovoUsuarioDialog
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListaUsuarioFragment : Fragment() {

    private var _binding: FragmentListaUsuarioBinding? = null
    private val binding = _binding!!
    private val listaAdapter by lazy { ListaUsuarioAdapter(requireContext()) }
    private val listaUsuarioDataholder by lazy {
        val appContainer by lazy { (requireContext().applicationContext as LeilaoAluraApp).appContainer }
        val factory = Factory(appContainer.ioScope, appContainer.roomDao)
        ViewModelProvider(this, factory)[ListaUsuarioDataholder::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = run {
        _binding = FragmentListaUsuarioBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded && !isDetached) {
            requireActivity().actionBar?.title = "Usuários"
            binding.listaUsuarioRecicla.adapter = listaAdapter
            listaUsuarioDataholder.todos()
            binding.listaUsuarioFabAdd.setOnClickListener {
                NovoUsuarioDialog(
                    listener = object : NovoUsuarioDialog.UsuarioCriadoListener {
                        override fun criado(usuario: Usuario) {
                            lifecycleScope.launch {
                                listaUsuarioDataholder.run {
                                    buscaPorId(salva(usuario))?.let { usuarioSalvo ->
                                        listaAdapter.run {
                                            adiciona(usuarioSalvo)
                                            binding.listaUsuarioRecicla.smoothScrollToPosition(
                                                itemCount - 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                ).show(parentFragmentManager, "NOVO_USUARIO")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            listaUsuarioDataholder.usuarios.collectLatest { usuarios ->
                listaAdapter.adiciona(usuarios)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}