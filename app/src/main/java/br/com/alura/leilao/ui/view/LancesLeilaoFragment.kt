package br.com.alura.leilao.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import br.com.alura.leilao.LeilaoAluraApp
import br.com.alura.leilao.databinding.FragmentLancesLeilaoBinding
import br.com.alura.leilao.dataholder.LeilaoDataholder
import br.com.alura.leilao.dataholder.UsuarioDataholder
import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.dialog.NovoLanceDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LancesLeilaoFragment : Fragment() {

    private var _binding: FragmentLancesLeilaoBinding? = null
    private val binding get() = _binding!!
    private val appContainer by lazy { (requireContext().applicationContext as LeilaoAluraApp).appContainer }
    private val usuarioDataholder by lazy {
        val factory = UsuarioDataholder.Factory(appContainer.ioScope, appContainer.roomDao)
        ViewModelProvider(this, factory)[UsuarioDataholder::class.java]
    }
    private val leilaoDataholder by lazy {
        val factory = LeilaoDataholder.Factory(appContainer.ioScope, appContainer.webClient)
        ViewModelProvider(this, factory)[LeilaoDataholder::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val containerId = (binding.root.parent as FragmentContainerView).id
            replaceFragment(containerId, ListaLeilaoFragment(), "ListaLeilao")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLancesLeilaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.title = "Lances do leilão"
        arguments?.getSerializable(CHAVE_LEILAO)?.let { leilao ->
            applyLeilaoDetails(leilao as Leilao)
            setupFabClickListener(leilao)
        } ?: run {
            requireActivity().finish()
        }
    }

    private fun setupFabClickListener(leilao: Leilao) {
        binding.lancesLeilaoFabAdicionaFrag.setOnClickListener {
            val containerId = (binding.root.parent as FragmentContainerView).id
            appContainer.ioScope.launch {
                val usuarios = usuarioDataholder.todos()
                withContext(appContainer.uiScope.coroutineContext) {
                    showNovoLanceDialog(containerId, usuarios, leilao)
                }
            }
        }
    }

    private fun showNovoLanceDialog(containerId: Int, usuarios: List<Usuario>, leilao: Leilao) {
        NovoLanceDialog(
            containerId,
            usuarios,
            object : NovoLanceDialog.LanceCriadoListener {
                override fun lanceCriado(lance: Lance) {
                    leilaoDataholder.propoe(
                        lance = lance,
                        leilao = leilao,
                        lanceListener = object : LeilaoDataholder.LanceProcessadoListener {
                            override fun processado(leilao: Leilao) {
                                applyLeilaoDetails(leilao)
                            }
                        },
                        exceptionListener = object : LeilaoDataholder.PropoeExceptionListener {
                            override fun handleException(e: RuntimeException) {
                                showExceptionDialog(e)
                            }
                        },
                        falhaEnvioListener = object : LeilaoDataholder.FalhaEnvioListener {
                            override fun reportaFalha() {
                                displayFailToSendToast()
                            }
                        }
                    )
                }
            }
        ).show(childFragmentManager, "LanceLeilao")
    }

    private fun displayFailToSendToast() {
        Toast.makeText(
            requireContext(),
            "Não foi possível enviar Lance",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun applyLeilaoDetails(leilao: Leilao) {
        binding.lancesLeilaoDescricaoFrag.text = leilao.descricao
        binding.lancesLeilaoMaiorLanceFrag.text = leilao.maiorLanceFormatado
        binding.lancesLeilaoMenorLanceFrag.text = leilao.menorLanceFormatado
        binding.lancesLeilaoMaioresLancesFrag.text = getMaioresLances(leilao)
    }

    private fun showExceptionDialog(e: RuntimeException) {
        val message = when (e) {
            is QuantidadeMaximaDeLancesException,
            is ValorMenorQueOAnteriorException,
            is UsuarioDeuLancesSeguidosException -> e.message

            else -> "Erro desconhecido"
        }
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

    private fun getMaioresLances(leilao: Leilao) =
        if (leilao.lances[0].valor != 0.0) {
            leilao.tresMaioresLances.joinToString(separator = "\n") {
                "- ${it.valorFormatado} - (${it.usuario.id}) ${it.usuario.nome}"
            }
        } else {
            ""
        }
}