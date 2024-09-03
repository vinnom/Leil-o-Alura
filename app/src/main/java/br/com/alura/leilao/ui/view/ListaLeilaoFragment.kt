package br.com.alura.leilao.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.alura.leilao.LeilaoAluraApp
import br.com.alura.leilao.R
import br.com.alura.leilao.databinding.FragmentListaLeilaoBinding
import br.com.alura.leilao.dataholder.LeilaoDataholder
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListaLeilaoFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListaLeilaoBinding? = null
    private val binding get() = _binding!!
    private val listaAdapter by lazy { ListaLeilaoAdapter(requireContext()) }
    private val containerId by lazy { (binding.root.parent as FragmentContainerView).id }
    private val leilaoDataholder by lazy {
        val appContainer = (requireContext().applicationContext as LeilaoAluraApp).appContainer
        val factory = LeilaoDataholder.Factory(appContainer.ioScope, appContainer.webClient)
        ViewModelProvider(this, factory)[LeilaoDataholder::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaLeilaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded && !isDetached) {
            setupActionBar()
            setupItemClickListener()
            binding.listaLeilaoRecicla.adapter = listaAdapter
        }
    }

    private fun setupActionBar() {
        requireActivity().run {
            actionBar?.title = "Leilões"
            addMenuProvider(
                provider = this@ListaLeilaoFragment,
                owner = viewLifecycleOwner,
                state = Lifecycle.State.RESUMED
            )
        }
    }

    private fun setupItemClickListener() {
        listaAdapter.setOnItemClickListener(object : ListaLeilaoAdapter.OnItemClickListener {
            override fun onItemClick(leilao: Leilao) {
                navigateToLancesLeilaoFragment(leilao)
            }
        })
    }

    private fun navigateToLancesLeilaoFragment(leilao: Leilao) {
        val fragment = LancesLeilaoFragment().apply {
            arguments = Bundle().apply {
                putSerializable(CHAVE_LEILAO, leilao)
            }
        }
        replaceFragment(containerId, fragment, "LancesLeilao")
    }

    override fun onResume() {
        super.onResume()
        fetchLeiloes()
        observeLeiloes()
    }

    private fun fetchLeiloes() {
        leilaoDataholder.buscaLeiloes(object : LeilaoDataholder.ErroCarregamentoListener {
            override fun aoFalhar(mensagem: String?) {
                Toast.makeText(
                    requireContext(),
                    "Não foi possível carregar os leilões",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun observeLeiloes() {
        lifecycleScope.launch {
            leilaoDataholder.leiloes.collectLatest { leiloes ->
                listaAdapter.atualiza(leiloes)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.lista_leilao_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.lista_leilao_menu_usuarios -> navigateToListaUsuarioFragment()
        }
        return true
    }

    private fun navigateToListaUsuarioFragment() {
        replaceFragment(containerId, ListaUsuarioFragment(), "ListaUsuario")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}