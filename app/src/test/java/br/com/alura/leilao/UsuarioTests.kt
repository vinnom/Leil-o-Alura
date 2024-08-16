package br.com.alura.leilao.model

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class UsuarioTest {

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Given a new User, When id is 0, Then it should be created successfully`() {
        val user = Usuario(id = 0L, nome = "User")
        Assert.assertThat(user.id, CoreMatchers.equalTo(0L))
        Assert.assertThat(user.nome, CoreMatchers.equalTo("User"))
    }

    @Test
    fun `Given a new User, When id is not 0, Then it should be created successfully`() {
        val user = Usuario(id = 1L, nome = "User")
        Assert.assertThat(user.id, CoreMatchers.equalTo(1L))
        Assert.assertThat(user.nome, CoreMatchers.equalTo("User"))
    }

    @Test
    fun `Given a User, When toString is called, Then it should return the correct format`() {
        val user = Usuario(id = 1L, nome = "User")
        val expectedString = "(1) User"
        Assert.assertThat(user.toString(), CoreMatchers.equalTo(expectedString))
    }
}