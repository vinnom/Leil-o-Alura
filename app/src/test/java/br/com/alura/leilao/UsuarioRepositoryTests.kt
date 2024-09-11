package br.com.alura.leilao

import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.repository.UsuarioRepository
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UsuarioRepositoryTest {

    private lateinit var dao: UsuarioDAO
    private lateinit var repository: UsuarioRepository

    @Before
    fun setup() {
        dao = Mockito.mock(UsuarioDAO::class.java)
        repository = UsuarioRepository(dao)
    }

    @After
    fun teardown() {
        // Nothing to clean up after tests
    }

    @Test
    fun shouldReturnAllUsers() {
        val expectedUsers = listOf(Usuario(nome = "User1"), Usuario(nome = "User2"))
        `when`(dao.todos()).thenReturn(expectedUsers)

        val actualUsers = repository.todos()

        assertThat(actualUsers, equalTo(expectedUsers))
        verify(dao).todos()
    }

    @Test
    fun shouldSaveUser() {
        val user = Usuario(nome = "User1")

        repository.salva(user)

        verify(dao).salva(user)
    }

    @Test
    fun shouldFindUserById() {
        val user = Usuario(nome = "User1")
        val userId: Long = 1
        `when`(dao.buscaPorId(userId)).thenReturn(user)

        val actualUser = repository.buscaPorId(userId)

        assertThat(actualUser, equalTo(user))
        verify(dao).buscaPorId(userId)
    }
}