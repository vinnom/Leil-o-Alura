package br.com.alura.leilao.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.alura.leilao.LeilaoAluraApp
import br.com.alura.leilao.databinding.FragmentListaUsuarioBinding
import br.com.alura.leilao.dataholder.UsuarioDataholder
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.dialog.NovoUsuarioDialog
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.launch

class ListaUsuarioFragment : Fragment() {

    private var _binding: FragmentListaUsuarioBinding? = null
    private val binding get() = _binding!!
    private val listaAdapter by lazy { ListaUsuarioAdapter(requireContext()) }
    private val usuarioDataholder by lazy {
        val appContainer = (requireContext().applicationContext as LeilaoAluraApp).appContainer
        val factory = UsuarioDataholder.Factory(appContainer.ioScope, appContainer.roomDao)
        ViewModelProvider(this, factory)[UsuarioDataholder::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBackPressedCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded && !isDetached) {
            setupActionBar()
            setupRecyclerView()
            setupFabClickListener()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchUsuarios()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateToListaLeilaoFragment()
        }
    }

    private fun navigateToListaLeilaoFragment() {
        val containerId = (binding.root.parent as FragmentContainerView).id
        replaceFragment(containerId, ListaLeilaoFragment(), "ListaLeilao")
    }

    private fun setupActionBar() {
        requireActivity().actionBar?.title = "Usuários"
    }

    private fun setupRecyclerView() {
        binding.listaUsuarioRecicla.adapter = listaAdapter
    }

    private fun setupFabClickListener() {
        binding.listaUsuarioFabAdd.setOnClickListener {
            showNovoUsuarioDialog()
        }
    }

    private fun showNovoUsuarioDialog() {
        NovoUsuarioDialog(
            listener = object : NovoUsuarioDialog.UsuarioCriadoListener {
                override fun criado(usuario: Usuario) {
                    lifecycleScope.launch {
                        handleUsuarioCriado(usuario)
                    }
                }
            }
        ).show(parentFragmentManager, "NOVO_USUARIO")
    }

    private suspend fun handleUsuarioCriado(usuario: Usuario) {
        usuarioDataholder.run {
            buscaPorId(salva(usuario))?.let { usuarioSalvo ->
                listaAdapter.run {
                    adiciona(usuarioSalvo)
                    binding.listaUsuarioRecicla.smoothScrollToPosition(itemCount - 1)
                }
            }
        }
    }

    private fun fetchUsuarios() {
        lifecycleScope.launch {
            listaAdapter.adiciona(usuarioDataholder.todos())
        }
    }
}