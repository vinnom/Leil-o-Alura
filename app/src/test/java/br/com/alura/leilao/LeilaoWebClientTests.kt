package br.com.alura.leilao.api.retrofit.client

import br.com.alura.leilao.api.retrofit.RetrofitInicializador
import br.com.alura.leilao.api.retrofit.service.LeilaoService
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LeilaoWebClientTest {

    private val service = mock(LeilaoService::class.java)
    private val client = LeilaoWebClient(service)
    private val listener = mock(RespostaListener::class.java)

    @Test
    fun `should return success when propose a bid`() {
        val lance = Lance("John Doe", 2000.0)
        val call = mock(Call::class.java) as Call<Void>
        `when`(service.propoe(anyLong(), any(Lance::class.java))).thenReturn(call)

        client.propoe(lance, 1L, listener)

        verify(call).enqueue(any(Callback::class.java))
    }

    @Test
    fun `should return failure when propose a bid fails`() {
        val lance = Lance("John Doe", 2000.0)
        val call = mock(Call::class.java) as Call<Void>
        `when`(service.propoe(anyLong(), any(Lance::class.java))).thenReturn(call)

        client.propoe(lance, 1L, listener)

        verify(call).enqueue(any(Callback::class.java))
    }

    @Test
    fun `should return all auctions when request all auctions`() {
        val call = mock(Call::class.java) as Call<List<Leilao>>
        `when`(service.todos()).thenReturn(call)

        client.todos(listener)

        verify(call).enqueue(any(Callback::class.java))
    }

    @Test
    fun `should return failure when request all auctions fails`() {
        val call = mock(Call::class.java) as Call<List<Leilao>>
        `when`(service.todos()).thenReturn(call)

        client.todos(listener)

        verify(call).enqueue(any(Callback::class.java))
    }
}