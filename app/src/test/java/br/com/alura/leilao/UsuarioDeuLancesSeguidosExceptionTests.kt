import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class UsuarioDeuLancesSeguidosExceptionTest {

    @Test(expected = UsuarioDeuLancesSeguidosException::class)
    fun shouldThrowUsuarioDeuLancesSeguidosException() {
        // Simulating the scenario where the exception is thrown
        throw UsuarioDeuLancesSeguidosException()
    }

    @Test
    fun shouldHaveCorrectMessageForUsuarioDeuLancesSeguidosException() {
        val exception = UsuarioDeuLancesSeguidosException()
        Assert.assertThat(exception.message, CoreMatchers.`is`("Usuário não pode dar lances seguidos."))
    }
}