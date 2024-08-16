package br.com.alura.leilao.model.formatter

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FormatadorDeMoedaTest {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should return formatted value when formata is called`() {
        val formattedValue = FormatadorDeMoeda.formata(1000.0)
        Assert.assertThat(formattedValue, CoreMatchers.`is`("R$ 1.000,00"))
    }

    @Test
    fun `should return formatted value with cents when formata is called`() {
        val formattedValue = FormatadorDeMoeda.formata(1000.99)
        Assert.assertThat(formattedValue, CoreMatchers.`is`("R$ 1.000,99"))
    }

    @Test
    fun `should return formatted value when formata is called with zero`() {
        val formattedValue = FormatadorDeMoeda.formata(0.0)
        Assert.assertThat(formattedValue, CoreMatchers.`is`("R$ 0,00"))
    }

    @Test
    fun `should return formatted value when formata is called with negative value`() {
        val formattedValue = FormatadorDeMoeda.formata(-1000.0)
        Assert.assertThat(formattedValue, CoreMatchers.`is`("-R$ 1.000,00"))
    }
}