package br.com.alura.leilao.ui.activity

import android.content.Intent
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
import br.com.alura.leilao.dataholder.ListaLeilaoDataholder
import br.com.alura.leilao.dataholder.ListaLeilaoDataholder.Factory
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListaLeilaoFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListaLeilaoBinding? = null
    private val binding get() = _binding!!
    private val listaAdapter by lazy { ListaLeilaoAdapter(requireContext()) }
    private val listaLeilaoDataholder by lazy {
        val appContainer by lazy { (requireContext().applicationContext as LeilaoAluraApp).appContainer }
        val factory = Factory(appContainer.ioScope, appContainer.webClient)
        ViewModelProvider(this, factory)[ListaLeilaoDataholder::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = run {
        _binding = FragmentListaLeilaoBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded && !isDetached) {
            requireActivity().run {
                actionBar?.title = "Leilões"
                addMenuProvider(
                    this@ListaLeilaoFragment,
                    viewLifecycleOwner,
                    Lifecycle.State.RESUMED
                )
            }
            listaAdapter.setOnItemClickListener(object : ListaLeilaoAdapter.OnItemClickListener {
                override fun onItemClick(leilao: Leilao) {
                    parentFragmentManager.run {
                        beginTransaction().run {
                            val fragment = LancesLeilaoFragment().apply {
                                arguments = Bundle().apply {
                                    putSerializable(CHAVE_LEILAO, leilao)
                                }
                            }
                            val parentId = (binding.root.parent as FragmentContainerView).id
                            replace(parentId, fragment, "LancesLeilao")
                            commit()
                        }
                    }
                }
            })
            binding.listaLeilaoRecicla.adapter = listaAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        listaLeilaoDataholder.buscaLeiloes(
            listener = object : ListaLeilaoDataholder.ErroCarregamentoListener {
                override fun aoFalhar(mensagem: String?) {
                    Toast.makeText(
                        requireContext(),
                        "Não foi possível carregar os leilões",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        lifecycleScope.launch {
            listaLeilaoDataholder.leiloes.collectLatest { leiloes ->
                listaAdapter.atualiza(leiloes)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.lista_leilao_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.lista_leilao_menu_usuarios -> {
                val intent = Intent(requireContext(), ListaUsuarioActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}