package br.com.alura.leilao

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.repository.LeilaoRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LeilaoRepositoryTest {

    @Mock
    private lateinit var client: LeilaoWebClient

    @Mock
    private lateinit var listener: RespostaListener<Any>

    @Captor
    private lateinit var captor: ArgumentCaptor<RespostaListener<Any>>

    private lateinit var repository: LeilaoRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = LeilaoRepository(client)
    }

    @After
    fun teardown() {
        clearInvocations(client, listener)
    }

//    @Test
//    fun should_CallClientTodos_When_GetAllAuctions() {
//        repository.todos(listener)
//
//        verify(client).todos(captor.capture())
//        assertThat(captor.value, `is`(notNullValue()))
//    }

//    @Test
//    fun should_CallClientPropoe_When_ProposeBid() {
//        val lance = Lance(Usuario(nome = "user"), 1000.0)
//        val leilaoId = 1L
//
//        repository.propoe(lance, leilaoId, listener)
//
//        verify(client).propoe(eq(lance), eq(leilaoId), captor.capture())
//        assertThat(captor.value, `is`(notNullValue()))
//    }
}