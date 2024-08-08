package br.com.alura.leilao.ui

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.api.retrofit.client.RespostaListener
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter

class AtualizadorDeLeilao {

    fun buscaLeiloesNaAPIWeb(
        adapter: ListaLeilaoAdapter,
        client: LeilaoWebClient,
        erroCarregamentoListener: ErroCarregamentoListener
    ) {
        client.todos(object : RespostaListener<List<Leilao>> {
            override fun sucesso(resposta: List<Leilao>?) {
                adapter.atualiza(resposta)
            }

            override fun falha(mensagem: String?) {
                erroCarregamentoListener.aoFalhar(mensagem)
            }
        })
    }

    interface ErroCarregamentoListener {
        fun aoFalhar(mensagem: String?)
    }
}
