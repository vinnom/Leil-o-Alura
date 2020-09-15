package br.com.alura.leilao.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeilaoTest{

   @Mock
   private LeilaoWebClient cliente;
   @Mock
   private ListaLeilaoAdapter adapter;
   @Mock
   private AtualizadorDeLeilao.ErroCarregamentoListener listener;

   @Test
   public void deve_ObterSucessoNaRequisicao_QuandoBuscaNaAPIWeb(){
      doAnswer(new Answer(){
         @Override
         public Object answer(InvocationOnMock invocation){
            RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
            argument.sucesso(Arrays.asList(
               new Leilao("A"),
               new Leilao("B")
            ));
            return null;
         }
      }).when(cliente).todos(any(RespostaListener.class));

      new AtualizadorDeLeilao().buscaLeiloesNaAPIWeb(adapter, cliente, listener);

      verify(cliente).todos(any(RespostaListener.class));
      verify(adapter).atualiza(Arrays.asList(
         new Leilao("A"),
         new Leilao("B")
      ));
   }

   @Test
   public void deve_ObterFalhaNaRequisicao_QuandoBuscaNaAPIWeb(){
      doAnswer(new Answer(){
         @Override
         public Object answer(InvocationOnMock invocation){
            RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
            argument.falha(anyString());
            return null;
         }
      }).when(cliente).todos(any(RespostaListener.class));

      new AtualizadorDeLeilao().buscaLeiloesNaAPIWeb(adapter, cliente, listener);

      verify(cliente).todos(any(RespostaListener.class));
      verify(listener).aoFalhar(anyString());
   }

}