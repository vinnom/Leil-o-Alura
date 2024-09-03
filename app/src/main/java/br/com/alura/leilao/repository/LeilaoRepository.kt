package br.com.alura.leilao.repository

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao

class LeilaoRepository(private val client: LeilaoWebClient) {
    fun todos(listener: RespostaListener<List<Leilao>>) = client.todos(listener)
    fun propoe(lance: Lance, leilaoId: Long, listener: RespostaListener<Void>) =
        client.propoe(lance, leilaoId, listener)
}