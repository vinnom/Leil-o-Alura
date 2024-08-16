package br.com.alura.leilao.exception

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsuarioDeuLancesSeguidosExceptionTest {

    @Test
    fun should_ThrowException_When_UserBidsInSequence() {
        // Arrange
        val exception = UsuarioDeuLancesSeguidosException()

        // Act
        val result = Assert.assertThrows(UsuarioDeuLancesSeguidosException::class.java) {
            throw exception
        }

        // Assert
        Assert.assertThat(result, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }
}