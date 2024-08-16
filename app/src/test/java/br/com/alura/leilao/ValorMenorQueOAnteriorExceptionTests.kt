import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class ValorMenorQueOAnteriorExceptionTest {

    @Test(expected = ValorMenorQueOAnteriorException::class)
    fun shouldThrowValorMenorQueOAnteriorException() {
        // Considering the exception is thrown when a value less than the previous one is tried to be set
        throw ValorMenorQueOAnteriorException()
    }

    @Test
    fun shouldHaveCorrectErrorMessage() {
        // Considering the exception is thrown with a custom error message
        val exception = ValorMenorQueOAnteriorException()
        Assert.assertThat(exception.message, CoreMatchers.equalTo("Valor menor que o anterior"))
    }
}