package br.com.alura.leilao.exception

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.Rule
import org.junit.rules.ExpectedException

class QuantidadeMaximaDeLancesExceptionTest {

    @Rule @JvmField
    var thrown = ExpectedException.none()

    // Test to verify if the exception is thrown when expected
    @Test(expected = QuantidadeMaximaDeLancesException::class)
    fun shouldThrowQuantidadeMaximaDeLancesException() {
        throw QuantidadeMaximaDeLancesException()
    }

    // Test to verify the message of the exception
    @Test
    fun shouldHaveCorrectMessage() {
        thrown.expect(QuantidadeMaximaDeLancesException::class.java)
        thrown.expectMessage(CoreMatchers.equalTo("Quantidade máxima de lances atingida"))
        throw QuantidadeMaximaDeLancesException()
    }
}