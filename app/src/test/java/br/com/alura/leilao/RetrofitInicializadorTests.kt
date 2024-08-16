package br.com.alura.leilao.api.retrofit

import br.com.alura.leilao.BuildConfig
import br.com.alura.leilao.api.retrofit.service.LeilaoService
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInicializadorTest {

    private lateinit var retrofitInicializador: RetrofitInicializador

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        retrofitInicializador = RetrofitInicializador()
    }

    @Test
    fun whenRetrofitIsInitialized_thenLeilaoServiceIsNotNull() = runBlockingTest {
        val leilaoService = retrofitInicializador.leilaoService
        assertThat(leilaoService, `is`(notNullValue()))
    }

    @Test
    fun whenRetrofitIsInitialized_thenBaseUrlIsCorrect() = runBlockingTest {
        val retrofit = retrofitInicializador.retrofit
        assertThat(retrofit.baseUrl().toString(), `is`(BuildConfig.URL_BASE))
    }

    @Test
    fun whenRetrofitIsInitialized_thenClientIsOkHttpClient() = runBlockingTest {
        val retrofit = retrofitInicializador.retrofit
        assertThat(retrofit.callFactory(), `is`(OkHttpClient::class.java))
    }

    @Test
    fun whenRetrofitIsInitialized_thenLoggingInterceptorIsAdded() = runBlockingTest {
        val retrofit = retrofitInicializador.retrofit
        val client = retrofit.callFactory() as OkHttpClient
        val interceptor = client.interceptors().first()
        assertThat(interceptor, `is`(HttpLoggingInterceptor::class.java))
    }

    @Test
    fun whenRetrofitIsInitialized_thenGsonConverterFactoryIsAdded() = runBlockingTest {
        val retrofit = retrofitInicializador.retrofit
        val converterFactories = retrofit.converterFactories()
        val gsonConverterFactory = converterFactories.find { it is GsonConverterFactory }
        assertThat(gsonConverterFactory, `is`(notNullValue()))
    }
}