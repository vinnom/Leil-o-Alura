package br.com.alura.leilao.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.leilao.database.AppDatabase
import br.com.alura.leilao.databinding.ActivityListaUsuarioBinding
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.AtualizadorDeUsuario
import br.com.alura.leilao.ui.dialog.NovoUsuarioDialog
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaUsuarioBinding
    private val dao by lazy {
        AppDatabase.getDatabase(this).usuarioDao()
    }
    private lateinit var adapter: ListaUsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Usuários"
        inicializaAtributos()
        configuraRecyclerView()
        configuraFab()
    }

    private fun inicializaAtributos() {
        adapter = ListaUsuarioAdapter(this)
    }

    private fun configuraFab() {
        binding.listaUsuarioFabAdiciona.setOnClickListener {
            mostraDialogAdicionaNovoUsuario()
        }
    }

    private fun configuraRecyclerView() {
        binding.listaUsuarioRecyclerview.adapter = adapter
        lifecycleScope.launch {
            val usuarios = withContext(Dispatchers.IO) {
                dao.todos()
            }
            adapter.adiciona(usuarios)
        }
    }

    private fun mostraDialogAdicionaNovoUsuario() {
        val dialog = NovoUsuarioDialog(
            this,
            object : NovoUsuarioDialog.UsuarioCriadoListener {
                override fun criado(usuario: Usuario) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            dao.salva(usuario)
                        }
                        AtualizadorDeUsuario(
                            dao,
                            adapter,
                            binding.listaUsuarioRecyclerview,
                            lifecycleScope
                        ).salva(usuario)
                    }
                }
            }
        )
        dialog.mostra()
    }
}
