package br.com.alura.leilao.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.leilao.R
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.databinding.ActivityListaLeilaoBinding
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.AtualizadorDeLeilao
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter

class ListaLeilaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaLeilaoBinding
    private val client = LeilaoWebClient()
    private lateinit var adapter: ListaLeilaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaLeilaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Leilões"
        configuraListaLeiloes()
    }

    private fun configuraListaLeiloes() {
        configuraAdapter()
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        binding.listaLeilaoRecyclerview.adapter = adapter
    }

    private fun configuraAdapter() {
        adapter = ListaLeilaoAdapter(this)
        adapter.setOnItemClickListener(object : ListaLeilaoAdapter.OnItemClickListener {
            override fun onItemClick(leilao: Leilao) {
                vaiParaTelaDeLances(leilao)
            }
        })
    }

    private fun vaiParaTelaDeLances(leilao: Leilao) {
        val intent = Intent(this, LancesLeilaoActivity::class.java)
        intent.putExtra(CHAVE_LEILAO, leilao)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        AtualizadorDeLeilao().buscaLeiloesNaAPIWeb(
            adapter, client,
            object : AtualizadorDeLeilao.ErroCarregamentoListener {
                override fun aoFalhar(mensagem: String) {
                    exibeToastFalha()
                }
            }
        )
    }

    private fun exibeToastFalha() {
        Toast.makeText(this, "Não foi possível carregar os leilões", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.lista_leilao_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.lista_leilao_menu_usuarios -> {
                val intent = Intent(this, ListaUsuarioActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}