package br.com.alura.leilao

import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.hamcrest.CoreMatchers.equalTo

class UsuarioDeuLancesSeguidosExceptionTest {

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun shouldThrowUsuarioDeuLancesSeguidosException() {
        // Arrange
        val expectedMessage = "O mesmo usuário do último lance não pode propror novos lances"

        // Assert
        expectedException.expect(UsuarioDeuLancesSeguidosException::class.java)
        expectedException.expectMessage(equalTo(expectedMessage))

        // Act
        throw UsuarioDeuLancesSeguidosException()
    }

    @Test
    fun shouldThrowUsuarioDeuLancesSeguidosExceptionWithCustomMessage() {
        // Arrange
        val expectedMessage = "Custom message"

        // Assert
        expectedException.expect(UsuarioDeuLancesSeguidosException::class.java)
        expectedException.expectMessage(equalTo(expectedMessage))

        // Act
        throw UsuarioDeuLancesSeguidosException(expectedMessage)
    }
}