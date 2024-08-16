package br.com.alura.leilao.model

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsuarioTest {

    @Test
    fun `when creating user, should set id and name correctly`() {
        val id = 1L
        val name = "Test User"
        val user = Usuario(id, name)

        assertThat(user.id, equalTo(id))
        assertThat(user.nome, equalTo(name))
    }

    @Test
    fun `when creating user without id, should set id to 0 and name correctly`() {
        val name = "Test User"
        val user = Usuario(nome = name)

        assertThat(user.id, equalTo(0L))
        assertThat(user.nome, equalTo(name))
    }

    @Test
    fun `when calling toString, should return formatted string`() {
        val id = 1L
        val name = "Test User"
        val user = Usuario(id, name)

        val expectedString = "($id) $name"
        assertThat(user.toString(), equalTo(expectedString))
    }
}