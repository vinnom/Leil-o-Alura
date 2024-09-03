package br.com.alura.leilao.api.retrofit.client

import br.com.alura.leilao.api.retrofit.RetrofitInicializador
import br.com.alura.leilao.api.retrofit.service.LeilaoService
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class LeilaoWebClient {

    private val service: LeilaoService = RetrofitInicializador().leilaoService

    fun propoe(lance: Lance, id: Long, listener: RespostaListener<Void>) {
        val call = service.propoe(id, lance)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    listener.sucesso(null)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                listener.falha(t.message ?: "Unknown error")
            }
        })
    }

    fun todos(listener: RespostaListener<List<Leilao>>) {
        val call = service.todos()
        call.enqueue(object : Callback<List<Leilao>> {
            override fun onResponse(call: Call<List<Leilao>>, response: Response<List<Leilao>>) {
                if (temDados(response)) {
                    listener.sucesso(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<Leilao>>, t: Throwable) {
                listener.falha(t.message ?: "Erro desconhecido")
            }
        })
    }

    private fun temDados(response: Response<List<Leilao>>): Boolean {
        return response.isSuccessful && response.body() != null
    }
}