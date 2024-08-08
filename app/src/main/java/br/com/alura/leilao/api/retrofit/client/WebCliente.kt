package br.com.alura.leilao.api.retrofit.client

import retrofit2.Response

abstract class WebCliente {
    protected fun <T> temDados(response: Response<T?>) =
            response.isSuccessful && response.body() != null
}