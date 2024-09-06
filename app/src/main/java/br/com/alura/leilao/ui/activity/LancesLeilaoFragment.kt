package br.com.alura.leilao.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.alura.leilao.LeilaoAluraApp
import br.com.alura.leilao.databinding.FragmentLancesLeilaoBinding
import br.com.alura.leilao.dataholder.LeilaoDataholder
import br.com.alura.leilao.dataholder.UsuarioDataholder
import br.com.alura.leilao.dataholder.UsuarioDataholder.Factory
import br.com.alura.leilao.model.Leilao

class LancesLeilaoFragment : Fragment() {

    private var leilaoRecebido: Leilao? = null
    private var _binding: FragmentLancesLeilaoBinding? = null
    private val binding = _binding!!
    private val appContainer by lazy { (requireContext().applicationContext as LeilaoAluraApp).appContainer }
    private val usuarioDataholder by lazy {
        val factory = Factory(appContainer.ioScope, appContainer.roomDao)
        ViewModelProvider(this, factory)[UsuarioDataholder::class.java]
    }
    private val leilaoDataholder by lazy {
        val factory = LeilaoDataholder.Factory(appContainer.ioScope, appContainer.webClient)
        ViewModelProvider(this, factory)[LeilaoDataholder::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = run {
        _binding = FragmentLancesLeilaoBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded && !isDetached) {
            requireActivity().run {
                actionBar?.title = "Lances do leilão"
                if (intent.hasExtra(CHAVE_LEILAO)) {
                    leilaoRecebido = intent.getSerializableExtra(CHAVE_LEILAO) as Leilao
                    leilaoRecebido?.run {
                        binding.lancesLeilaoDescricaoFrag.text = descricao
                        binding.lancesLeilaoMaiorLanceFrag.text = getMaiorLanceFormatado()
                        binding.lancesLeilaoMenorLanceFrag.text = getMenorLanceFormatado()
                        binding.lancesLeilaoMaioresLancesFrag.text = getMaioresLances(this)
                    }
                    binding.lancesLeilaoFabAdicionaFrag.setOnClickListener {
                        Log.d(LancesLeilaoFragment::class.simpleName, "onViewCreated: fab clicado")
                    }
                } else {
                    finish()
                }
            }
        }
    }

    private fun getMaioresLances(leilao: Leilao): String {
        var maioresLances = ""
        if (leilao.lances[0].valor != 0.0) {
            for (lance in leilao.getTresMaioresLances()) {
                lance.run {
                    maioresLances += "- $valorFormatado - (${usuario.id}) ${usuario.nome}\n"
                }
            }
        }
        return maioresLances
    }
}