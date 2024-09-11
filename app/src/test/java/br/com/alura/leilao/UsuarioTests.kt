package br.com.alura.leilao

import br.com.alura.leilao.model.Usuario
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class UsuarioTest {

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testUsuarioToString() {
        val usuario = Usuario(id = 1L, nome = "Test User")
        val expectedString = "(1) Test User"
        assertThat(usuario.toString(), equalTo(expectedString))
    }

    @Test
    fun testUsuarioEquals() {
        val usuario1 = Usuario(id = 1L, nome = "Test User")
        val usuario2 = Usuario(id = 1L, nome = "Test User")
        assertThat(usuario1, equalTo(usuario2))
    }

    @Test
    fun testUsuarioNotEquals() {
        val usuario1 = Usuario(id = 1L, nome = "Test User")
        val usuario2 = Usuario(id = 2L, nome = "Test User 2")
        assertThat(usuario1, not(equalTo(usuario2)))
    }

    @Test
    fun testUsuarioHashCode() {
        val usuario1 = Usuario(id = 1L, nome = "Test User")
        val usuario2 = Usuario(id = 1L, nome = "Test User")
        assertThat(usuario1.hashCode(), equalTo(usuario2.hashCode()))
    }

    @Test
    fun testUsuarioHashCodeNotEquals() {
        val usuario1 = Usuario(id = 1L, nome = "Test User")
        val usuario2 = Usuario(id = 2L, nome = "Test User 2")
        assertThat(usuario1.hashCode(), not(equalTo(usuario2.hashCode())))
    }

    @After
    fun tearDown() {
        // Here we can add any necessary cleanup code after each test if needed.
    }
}