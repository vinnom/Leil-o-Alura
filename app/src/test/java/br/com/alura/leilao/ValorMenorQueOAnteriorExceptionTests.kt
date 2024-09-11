package br.com.alura.leilao

import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ValorMenorQueOAnteriorExceptionTest {

    private lateinit var exception: ValorMenorQueOAnteriorException

    @Before
    fun setup() {
        // Initialize the exception with default message
        exception = ValorMenorQueOAnteriorException()
    }

    @After
    fun tearDown() {
        // Nothing to clean up after test
    }

    @Test
    fun should_ThrowException_When_DefaultMessageIsCalled() {
        // Assert that the exception message is equal to the default message
        assertThat(exception.message, equalTo("Lance precisa ser maior que o último lance"))
    }

    @Test
    fun should_ThrowException_When_CustomMessageIsCalled() {
        // Initialize the exception with a custom message
        val customException = ValorMenorQueOAnteriorException("Custom exception message")

        // Assert that the exception message is equal to the custom message
        assertThat(customException.message, equalTo("Custom exception message"))
    }
}