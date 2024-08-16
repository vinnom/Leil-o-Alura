package br.com.alura.leilao.exception

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class QuantidadeMaximaDeLancesExceptionTest {

    @Test
    fun `should Throw QuantidadeMaximaDeLancesException`() {
        // Given
        val exception = QuantidadeMaximaDeLancesException()

        // When
        val thrownException = Assert.assertThrows(QuantidadeMaximaDeLancesException::class.java) {
            throw exception
        }

        // Then
        Assert.assertThat(thrownException, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }
}