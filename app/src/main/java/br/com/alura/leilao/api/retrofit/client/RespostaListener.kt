package br.com.alura.leilao.api.retrofit.client

interface RespostaListener<T> {
    fun sucesso(resposta: T?)
    fun falha(mensagem: String?)
}