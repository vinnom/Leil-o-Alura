package br.com.alura.leilao.ui

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizadorDeUsuario(
    private val dao: UsuarioDAO,
    private val adapter: ListaUsuarioAdapter,
    private val recyclerView: RecyclerView,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    fun salva(usuario: Usuario) {
        lifecycleScope.launch(Dispatchers.IO) {
            val usuarioSalvoId = dao.salva(usuario)
            dao.buscaPorId(usuarioSalvoId)?.let {
                withContext(Dispatchers.Main) { atualizaNaLista(it) }
            }
        }
    }

    private fun atualizaNaLista(usuario: Usuario) {
        adapter.adiciona(usuario)
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }
}
