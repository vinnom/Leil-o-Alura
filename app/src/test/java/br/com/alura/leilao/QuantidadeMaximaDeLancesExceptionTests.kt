package br.com.alura.leilao

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test

class QuantidadeMaximaDeLancesExceptionTest {

    @Test
    fun should_ThrowQuantidadeMaximaDeLancesException_When_UserExceedsMaxBids() {
        try {
            throw QuantidadeMaximaDeLancesException()
        } catch (e: QuantidadeMaximaDeLancesException) {
            assertThat(
                e.message,
                CoreMatchers.`is`("Usuário não pode dar mais de cinco lances no mesmo leilão")
            )
        }
    }

    @Test
    fun should_ThrowQuantidadeMaximaDeLancesException_WithCustomMessage_When_UserExceedsMaxBids() {
        val customMessage = "Custom message for exceeding max bids"
        try {
            throw QuantidadeMaximaDeLancesException(customMessage)
        } catch (e: QuantidadeMaximaDeLancesException) {
            assertThat(e.message, CoreMatchers.`is`(customMessage))
        }
    }
}