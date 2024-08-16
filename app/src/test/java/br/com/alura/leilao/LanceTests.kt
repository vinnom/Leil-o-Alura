package br.com.alura.leilao.model

import br.com.alura.leilao.model.formatter.FormatadorDeMoeda
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertThat
import org.junit.Assert.assertEquals
import org.mockito.MockitoAnnotations
import org.mockito.Mockito

class LanceTest {

    private val usuario1 = Usuario(nome = "Usuario1")
    private val usuario2 = Usuario(nome = "Usuario2")

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `When compare two lances, if lance value is greater than other, should return -1`() {
        val lance1 = Lance(usuario = usuario1, valor = 200.0)
        val lance2 = Lance(usuario = usuario2, valor = 100.0)

        val result = lance1.compareTo(lance2)

        assertEquals(-1, result)
    }

    @Test
    fun `When compare two lances, if lance value is less than other, should return 1`() {
        val lance1 = Lance(usuario = usuario1, valor = 100.0)
        val lance2 = Lance(usuario = usuario2, valor = 200.0)

        val result = lance1.compareTo(lance2)

        assertEquals(1, result)
    }

    @Test
    fun `When compare two lances with same value, should return 0`() {
        val lance1 = Lance(usuario = usuario1, valor = 200.0)
        val lance2 = Lance(usuario = usuario2, valor = 200.0)

        val result = lance1.compareTo(lance2)

        assertEquals(0, result)
    }

    @Test
    fun `When lance is created, should format value correctly`() {
        val lance = Lance(usuario = usuario1, valor = 200.0)
        val expectedValue = FormatadorDeMoeda.formata(200.0)

        assertThat(lance.valorFormatado, CoreMatchers.`is`(expectedValue))
    }
}