package br.com.alura.leilao.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.leilao.api.EnviadorDeLance
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.database.AppDatabase
import br.com.alura.leilao.databinding.ActivityLancesLeilaoBinding
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.dialog.AvisoDialogManager
import br.com.alura.leilao.ui.dialog.NovoLanceDialog

class LancesLeilaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLancesLeilaoBinding
    private val client = LeilaoWebClient()
    private val dialogManager = AvisoDialogManager(this)
    private val dao by lazy {
        AppDatabase.getDatabase(this).usuarioDao()
    }
    private lateinit var leilaoRecebido: Leilao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLancesLeilaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Lances do leilão"
        carregaLeilaoRecebido()
    }

    private fun carregaLeilaoRecebido() {
        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_LEILAO)) {
            leilaoRecebido =
                dadosRecebidos.getSerializableExtra(CHAVE_LEILAO) as Leilao
            apresentaDados()
            configuraFab()
        } else {
            finish()
        }
    }

    private fun configuraFab() {
        binding.lancesLeilaoFabAdiciona.setOnClickListener {
            mostraDialogNovoLance()
        }
    }

    private fun mostraDialogNovoLance() {
        val dialog = NovoLanceDialog(
            this,
            object : NovoLanceDialog.LanceCriadoListener {
                override fun lanceCriado(lance: Lance) {
                    enviaLance(lance)
                }
            },
            dao, dialogManager
        )
        dialog.mostra()
    }

    private fun enviaLance(lance: Lance) {
        val enviador = EnviadorDeLance(
            client,
            lanceProcessadoListener(),
            dialogManager
        )
        enviador.envia(leilaoRecebido, lance)
    }

    private fun lanceProcessadoListener() = object : EnviadorDeLance.LanceProcessadoListener {
        override fun processado(leilao: Leilao) {
            leilaoRecebido = leilao
            apresentaDados()
        }
    }

    private fun apresentaDados() {
        adicionaDescricao(leilaoRecebido)
        adicionaMaiorLance(leilaoRecebido)
        adicionaMenorLance(leilaoRecebido)
        adicionaMaioresLances(leilaoRecebido)
    }

    private fun adicionaMaioresLances(leilao: Leilao) {
        val stringBuilder = StringBuilder()
        if (leilao.lances[0].valor != 0.0) {
            for (lance in leilao.getTresMaioresLances()) {
                stringBuilder.append("- ")
                stringBuilder.append(lance.valorFormatado)
                stringBuilder.append(" - (")
                stringBuilder.append(lance.usuario.id)
                stringBuilder.append(") ")
                stringBuilder.append(lance.usuario.nome)
                stringBuilder.append("\n")
            }
        }
        binding.lancesLeilaoMaioresLances.text = stringBuilder.toString()
    }

    private fun adicionaMenorLance(leilao: Leilao) {
        binding.lancesLeilaoMenorLance.text = leilao.getMenorLanceFormatado()
    }

    private fun adicionaMaiorLance(leilao: Leilao) {
        binding.lancesLeilaoMaiorLance.text = leilao.getMaiorLanceFormatado()
    }

    private fun adicionaDescricao(leilao: Leilao) {
        binding.lancesLeilaoDescricao.text = leilao.descricao
    }
}