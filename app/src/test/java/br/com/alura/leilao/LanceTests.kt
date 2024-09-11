package br.com.alura.leilao

import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Usuario
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class LanceTest {

    private lateinit var usuario: Usuario
    private lateinit var lance: Lance

    @Before
    fun setup() {
        usuario = Usuario(nome = "Usuário")
        lance = Lance(usuario, 100.0)
    }

    @After
    fun teardown() {
        // cleanup resources if needed
    }

    @Test
    fun `when create Lance, then valorFormatado should be formatted`() {
        assertThat(lance.valorFormatado, equalTo("R$ 100,00"))
    }

    @Test
    fun `when compareTo with higher value, then should return -1`() {
        val higherLance = Lance(usuario, 200.0)
        assertThat(lance.compareTo(higherLance), equalTo(1))
    }

    @Test
    fun `when compareTo with lower value, then should return 1`() {
        val lowerLance = Lance(usuario, 50.0)
        assertThat(lance.compareTo(lowerLance), equalTo(-1))
    }

    @Test
    fun `when compareTo with equal value, then should return 0`() {
        val equalLance = Lance(usuario, 100.0)
        assertThat(lance.compareTo(equalLance), equalTo(0))
    }
}