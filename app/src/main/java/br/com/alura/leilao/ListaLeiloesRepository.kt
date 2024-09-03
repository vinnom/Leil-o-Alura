package br.com.alura.leilao

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao

class ListaLeiloesRepository(private val client: LeilaoWebClient) {
    fun todos(listener: RespostaListener<List<Leilao>>) =
        client.todos(listener)
}