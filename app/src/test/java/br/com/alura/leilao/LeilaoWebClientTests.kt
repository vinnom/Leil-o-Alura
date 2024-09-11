//import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
//import br.com.alura.leilao.api.retrofit.client.RespostaListener
//import br.com.alura.leilao.api.retrofit.service.LeilaoService
//import br.com.alura.leilao.model.Lance
//import br.com.alura.leilao.model.Leilao
//import org.hamcrest.CoreMatchers.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class LeilaoWebClientTest {
//
//    @Mock
//    private lateinit var service: LeilaoService
//
//    @Mock
//    private lateinit var respostaListener: RespostaListener<List<Leilao>>
//
//    @Mock
//    private lateinit var call: Call<List<Leilao>>
//
//    private lateinit var client: LeilaoWebClient
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        client = Mockito.spy(LeilaoWebClient())
//        Mockito.`when`(client.service).thenReturn(service)
//    }
//
//    @After
//    fun tearDown() {
//        Mockito.verifyNoMoreInteractions(service, respostaListener, call)
//    }
//
//    @Test
//    fun should_ReturnSuccess_WhenFetchAllAuctions() {
//        val leilao = Leilao(descricao = "Teste")
//        val response = Response.success(listOf(leilao))
//        Mockito.`when`(service.todos()).thenReturn(call)
//
//        Mockito.doAnswer { invocation ->
//            val callback = invocation.arguments[0] as Callback<List<Leilao>>
//            callback.onResponse(call, response)
//            null
//        }.`when`(call).enqueue(any())
//
//        client.todos(respostaListener)
//
//        Mockito.verify(respostaListener).sucesso(listOf(leilao))
//    }
//
//    @Test
//    fun should_ReturnFailure_WhenFetchAllAuctionsFails() {
//        val error = "Unknown error"
//        Mockito.`when`(service.todos()).thenReturn(call)
//
//        Mockito.doAnswer { invocation ->
//            val callback = invocation.arguments[0] as Callback<List<Leilao>>
//            callback.onFailure(call, Throwable(error))
//            null
//        }.`when`(call).enqueue(any())
//
//        client.todos(respostaListener)
//
//        Mockito.verify(respostaListener).falha(error)
//    }
//
//    @Test
//    fun should_ReturnSuccess_WhenProposeBid() {
//        val lance = Lance(null, 1000.0)
//        val response = Response.success<Void>(null)
//        Mockito.`when`(service.propoe(anyLong(), eq(lance))).thenReturn(call as Call<Void>)
//
//        Mockito.doAnswer { invocation ->
//            val callback = invocation.arguments[0] as Callback<Void>
//            callback.onResponse(call as Call<Void>, response)
//            null
//        }.`when`(call).enqueue(any())
//
//        client.propoe(lance, 1L, respostaListener as LeilaoWebClient.RespostaListener<Void>)
//
//        Mockito.verify(respostaListener as LeilaoWebClient.RespostaListener<Void>).sucesso(null)
//    }
//
//    @Test
//    fun should_ReturnFailure_WhenProposeBidFails() {
//        val error = "Unknown error"
//        val lance = Lance(null, 1000.0)
//        Mockito.`when`(service.propoe(anyLong(), eq(lance))).thenReturn(call as Call<Void>)
//
//        Mockito.doAnswer { invocation ->
//            val callback = invocation.arguments[0] as Callback<Void>
//            callback.onFailure(call as Call<Void>, Throwable(error))
//            null
//        }.`when`(call).enqueue(any())
//
//        client.propoe(lance, 1L, respostaListener as LeilaoWebClient.RespostaListener<Void>)
//
//        Mockito.verify(respostaListener as LeilaoWebClient.RespostaListener<Void>).falha(error)
//    }
//}