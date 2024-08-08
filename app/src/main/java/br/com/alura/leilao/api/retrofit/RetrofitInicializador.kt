package br.com.alura.leilao.api.retrofit

import br.com.alura.leilao.BuildConfig
import br.com.alura.leilao.api.retrofit.converter.StringConverterFactory
import br.com.alura.leilao.api.retrofit.service.LeilaoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInicializador {

    private val retrofit: Retrofit

    init {
        val client = configuraHttpClient()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(client)
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun configuraHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    val leilaoService: LeilaoService
        get() = retrofit.create(LeilaoService::class.java)
}