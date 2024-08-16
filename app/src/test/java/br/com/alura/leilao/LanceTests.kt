package br.com.alura.leilao.model

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito

class LanceTest {

    @Test
    fun `compareTo should return -1 when this value is greater than other value`() {
        val usuario1 = Mockito.mock(Usuario::class.java)
        val usuario2 = Mockito.mock(Usuario::class.java)
        val lance1 = Lance(usuario1, 200.0)
        val lance2 = Lance(usuario2, 100.0)

        val result = lance1.compareTo(lance2)

        assertThat(result, equalTo(-1))
    }

    @Test
    fun `compareTo should return 1 when this value is less than other value`() {
        val usuario1 = Mockito.mock(Usuario::class.java)
        val usuario2 = Mockito.mock(Usuario::class.java)
        val lance1 = Lance(usuario1, 100.0)
        val lance2 = Lance(usuario2, 200.0)

        val result = lance1.compareTo(lance2)

        assertThat(result, equalTo(1))
    }

    @Test
    fun `compareTo should return 0 when this value is equal to other value`() {
        val usuario1 = Mockito.mock(Usuario::class.java)
        val usuario2 = Mockito.mock(Usuario::class.java)
        val lance1 = Lance(usuario1, 100.0)
        val lance2 = Lance(usuario2, 100.0)

        val result = lance1.compareTo(lance2)

        assertThat(result, equalTo(0))
    }

    @Test
    fun `valorFormatado should return formatted value`() {
        val usuario = Mockito.mock(Usuario::class.java)
        val valor = 100.0
        val lance = Lance(usuario, valor)

        val result = lance.valorFormatado

        assertThat(result, equalTo("R$ 100,00"))
    }
}