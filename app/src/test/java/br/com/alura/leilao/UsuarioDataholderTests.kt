package br.com.alura.leilao

import br.com.alura.leilao.dataholder.UsuarioDataholder
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UsuarioDataholderTest {

    @Mock
    private lateinit var usuarioDAO: UsuarioDAO
    private lateinit var usuarioRepository: UsuarioRepository
    private lateinit var ioScope: CoroutineScope
    private lateinit var usuarioDataholder: UsuarioDataholder

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        ioScope = CoroutineScope(Dispatchers.IO)
        usuarioDataholder = UsuarioDataholder(ioScope, usuarioDAO)
        usuarioRepository = UsuarioRepository(usuarioDAO)
    }

    @After
    fun teardown() {
        ioScope.cancel()
    }

    @Test
    fun `when todos is called, should return all users from repository`() = runBlocking {
        val expectedUsers = listOf(Usuario(nome = "User1"), Usuario(nome = "User2"))
        Mockito.`when`(usuarioRepository.todos()).thenReturn(expectedUsers)

        val actualUsers = usuarioDataholder.todos()

        assertThat(actualUsers, equalTo(expectedUsers))
    }

    @Test
    fun `when buscaPorId is called with id, should return user with same id from repository`() = runBlocking {
        val expectedUser = Usuario(nome = "User1")
        val id = 1L
        Mockito.`when`(usuarioRepository.buscaPorId(id)).thenReturn(expectedUser)

        val actualUser = usuarioDataholder.buscaPorId(id)

        assertThat(actualUser, equalTo(expectedUser))
    }
}