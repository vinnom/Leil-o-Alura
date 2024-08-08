package br.com.alura.leilao.ui

import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter

class AtualizadorDeUsuario(
    private val dao: UsuarioDAO,
    private val adapter: ListaUsuarioAdapter,
    private val recyclerView: RecyclerView
) {

    fun salva(usuario: Usuario) {
        val usuarioSalvo = dao.salva(usuario)
        atualizaNaLista(usuarioSalvo)
    }

    private fun atualizaNaLista(usuario: Usuario) {
        adapter.adiciona(usuario)
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }
}
