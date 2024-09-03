package br.com.alura.leilao.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.databinding.FragmentLancesLeilaoBinding
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.activity.CHAVE_LEILAO
import br.com.alura.leilao.ui.dialog.AvisoDialogManager

class LancesLeilaoFragment : Fragment() {

    private var leilaoRecebido: Leilao? = null
    private var _binding: FragmentLancesLeilaoBinding? = null
    private val binding = _binding!!
    private val webClient by lazy { LeilaoWebClient() }
    private val dialogManager by lazy { AvisoDialogManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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