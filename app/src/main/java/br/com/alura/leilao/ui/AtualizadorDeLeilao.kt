package br.com.alura.leilao.ui;

import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

public class AtualizadorDeLeilao{

   public void buscaLeiloesNaAPIWeb(
      final ListaLeilaoAdapter adapter,
      LeilaoWebClient client,
      final ErroCarregamentoListener erroCarregamentoListener)
   {
      client.todos(new RespostaListener<List<Leilao>>(){
         @Override
         public void sucesso(List<Leilao> leiloes){
            adapter.atualiza(leiloes);
         }

         @Override
         public void falha(String mensagem){
            erroCarregamentoListener.aoFalhar(mensagem);
         }
      });
   }

   public interface ErroCarregamentoListener{
      void aoFalhar(String mensagem);
   }
}
