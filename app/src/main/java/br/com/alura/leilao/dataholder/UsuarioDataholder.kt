package br.com.alura.leilao.dataholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class UsuarioDataholder(
    private val ioScope: CoroutineScope,
    dao: UsuarioDAO,
) : ViewModel() {

    private val repository = UsuarioRepository(dao)

    suspend fun todos() = ioScope.async { repository.todos() }.await()
    suspend fun buscaPorId(id: Long) = ioScope.async { repository.buscaPorId(id) }.await()
    suspend fun salva(usuario: Usuario) = ioScope.async { repository.salva(usuario) }.await()

    class Factory(
        private val ioScope: CoroutineScope,
        private val dao: UsuarioDAO,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(CoroutineScope::class.java, UsuarioDAO::class.java)
                .newInstance(ioScope, dao)
        }
    }
}