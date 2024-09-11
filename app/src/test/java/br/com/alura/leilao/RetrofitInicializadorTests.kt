//import br.com.alura.leilao.api.retrofit.RetrofitInicializador
//import br.com.alura.leilao.api.retrofit.service.LeilaoService
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.hamcrest.CoreMatchers
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class RetrofitInicializadorTest {
//
//    private lateinit var retrofitInicializador: RetrofitInicializador
//    private lateinit var mockRetrofit: Retrofit
//    private lateinit var mockOkHttpClient: OkHttpClient
//
//    @Before
//    fun setUp() {
//        mockOkHttpClient = Mockito.mock(OkHttpClient::class.java)
//        mockRetrofit = Mockito.mock(Retrofit::class.java)
//
//        retrofitInicializador = Mockito.spy(RetrofitInicializador::class.java)
//        Mockito.`when`(retrofitInicializador.configuraHttpClient()).thenReturn(mockOkHttpClient)
//        Mockito.`when`(retrofitInicializador.retrofit).thenReturn(mockRetrofit)
//    }
//
//    @Test
//    fun should_ReturnLeilaoService_When_GetLeilaoService() {
//        val leilaoService = Mockito.mock(LeilaoService::class.java)
//        Mockito.`when`(mockRetrofit.create(LeilaoService::class.java)).thenReturn(leilaoService)
//
//        val result = retrofitInicializador.leilaoService
//
//        org.hamcrest.MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(leilaoService)))
//    }
//
//    @Test
//    fun should_ReturnHttpClient_When_ConfiguraHttpClient() {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
//            .build()
//
//        val result = retrofitInicializador.configuraHttpClient()
//
//        org.hamcrest.MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(okHttpClient)))
//    }
//
//    @After
//    fun tearDown() {
//        Mockito.verifyNoMoreInteractions(mockRetrofit, mockOkHttpClient)
//    }
//}