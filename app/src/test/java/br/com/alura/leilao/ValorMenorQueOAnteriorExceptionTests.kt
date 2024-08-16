package br.com.alura.leilao.exception

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValorMenorQueOAnteriorExceptionTest {

    @Test
    fun shouldThrowValorMenorQueOAnteriorException() {
        // Given
        val exception = ValorMenorQueOAnteriorException()

        // When
        val thrown = Assert.assertThrows(ValorMenorQueOAnteriorException::class.java) {
            throw exception
        }

        // Then
        Assert.assertThat(thrown, CoreMatchers.instanceOf(ValorMenorQueOAnteriorException::class.java))
    }
}