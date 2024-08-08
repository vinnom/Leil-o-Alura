package br.com.alura.leilao.api.retrofit.service

import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface LeilaoService {

    @GET("leilao")
    fun todos(): Call<List<Leilao>>

    @PUT("leilao/{id}/lance")
    fun propoe(@Path("id") id: Long, @Body lance: Lance): Call<Void>
}