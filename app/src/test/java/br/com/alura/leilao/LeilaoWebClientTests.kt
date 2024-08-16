package br.com.alura.leilao.api.retrofit.client

import br.com.alura.leilao.api.retrofit.service.LeilaoService
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.util.*

class LeilaoWebClientTest {

    @Mock
    private lateinit var service: LeilaoService

    @Mock
    private lateinit var respostaListener: RespostaListener<List<Leilao>>

    @Mock
    private lateinit var call: Call<List<Leilao>>

    @Mock
    private lateinit var response: Response<List<Leilao>>

    private lateinit var client: LeilaoWebClient

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        client = spy(LeilaoWebClient())
        doReturn(service).`when`(client).service
    }

    @Test
    fun deve_RetornarListaDeLeiloes_QuandoReceberRespostaComDados() = runBlockingTest {
        val leiloes = listOf(Leilao("Leilao 1"), Leilao("Leilao 2"))
        `when`(service.todos()).thenReturn(call)
        `when`(call.enqueue(any())).thenAnswer {
            (it.arguments[0] as Callback<List<Leilao>>).onResponse(call, response)
        }
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(leiloes)

        client.todos(respostaListener)

        verify(respostaListener).sucesso(leiloes)
    }

    @Test
    fun deve_RetornarFalha_QuandoReceberRespostaSemDados() = runBlockingTest {
        `when`(service.todos()).thenReturn(call)
        `when`(call.enqueue(any())).thenAnswer {
            (it.arguments[0] as Callback<List<Leilao>>).onResponse(call, response)
        }
        `when`(response.isSuccessful).thenReturn(false)

        client.todos(respostaListener)

        verify(respostaListener).falha(anyString())
    }

    @Test
    fun deve_RetornarFalha_QuandoReceberRespostaComFalhaDeRede() = runBlockingTest {
        `when`(service.todos()).thenReturn(call)
        `when`(call.enqueue(any())).thenAnswer {
            (it.arguments[0] as Callback<List<Leilao>>).onFailure(call, Throwable("Network error"))
        }

        client.todos(respostaListener)

        verify(respostaListener).falha("Network error")
    }
}