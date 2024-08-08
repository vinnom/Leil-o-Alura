package br.com.alura.leilao.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.databinding.ActivityListaUsuarioBinding
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.AtualizadorDeUsuario
import br.com.alura.leilao.ui.dialog.NovoUsuarioDialog
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter

class ListaUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaUsuarioBinding
    private lateinit var dao: UsuarioDAO
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
        dao = UsuarioDAO(this)
        adapter = ListaUsuarioAdapter(this)
    }

    private fun configuraFab() {
        binding.listaUsuarioFabAdiciona.setOnClickListener {
            mostraDialogAdicionaNovoUsuario()
        }
    }

    private fun configuraRecyclerView() {
        binding.listaUsuarioRecyclerview.adapter = adapter
        adapter.adiciona(dao.todos())
    }

    private fun mostraDialogAdicionaNovoUsuario() {
        val dialog = NovoUsuarioDialog(
            this,
            object : NovoUsuarioDialog.UsuarioCriadoListener {
                override fun criado(usuario: Usuario) {
                    AtualizadorDeUsuario(
                        dao,
                        adapter,
                        binding.listaUsuarioRecyclerview
                    ).salva(usuario)
                }
            }
        )
        dialog.mostra()
    }
}