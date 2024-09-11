package br.com.alura.leilao

import br.com.alura.leilao.model.formatter.FormatadorDeMoeda
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.Before
import org.junit.After

class FormatadorDeMoedaTest {

    private lateinit var formatadorDeMoeda: FormatadorDeMoeda

    @Before
    fun setUp() {
        formatadorDeMoeda = FormatadorDeMoeda
    }

    @After
    fun tearDown() {
    }

    // Test to check if the function correctly formats a positive double value
    @Test
    fun `Given a positive double, When formata is called, Then return correct formatted string`() {
        val result = formatadorDeMoeda.formata(1000.0)
        assertThat(result, equalTo("R$ 1.000,00"))
    }

    // Test to check if the function correctly formats a negative double value
    @Test
    fun `Given a negative double, When formata is called, Then return correct formatted string`() {
        val result = formatadorDeMoeda.formata(-1000.0)
        assertThat(result, equalTo("-R$ 1.000,00"))
    }

    // Test to check if the function correctly formats a double value with more than two decimal places
    @Test
    fun `Given a double with more than two decimal places, When formata is called, Then return correct formatted string`() {
        val result = formatadorDeMoeda.formata(1000.1234)
        assertThat(result, equalTo("R$ 1.000,12"))
    }

    // Test to check if the function correctly formats a double value with less than two decimal places
    @Test
    fun `Given a double with less than two decimal places, When formata is called, Then return correct formatted string`() {
        val result = formatadorDeMoeda.formata(1000.1)
        assertThat(result, equalTo("R$ 1.000,10"))
    }

    // Test to check if the function correctly formats a zero double value
    @Test
    fun `Given a zero double, When formata is called, Then return correct formatted string`() {
        val result = formatadorDeMoeda.formata(0.0)
        assertThat(result, equalTo("R$ 0,00"))
    }
}