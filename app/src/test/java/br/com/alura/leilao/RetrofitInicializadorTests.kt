import br.com.alura.leilao.api.retrofit.RetrofitInicializador
import br.com.alura.leilao.api.retrofit.service.LeilaoService
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitInicializadorTest {

    private val inicializador = RetrofitInicializador()

    @Test
    fun should_ReturnLeilaoService_When_GetLeilaoService() {
        val service = inicializador.leilaoService
        assertThat(service, instanceOf(LeilaoService::class.java))
    }
}