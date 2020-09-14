package br.com.alura.leilao.ui.activity;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoActivityTest {

   @Mock
   private Context contexto;
   @Mock
   private LeilaoWebClient cliente;
   @Spy
   private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(contexto);

   @Test
   public void deve_ObterSucessoNaRequisicao_QuandoBuscaNaAPIWeb() {
      doNothing().when(adapter).notificaMudancaNoConjuntoDeDados();
      doAnswer(new Answer() {
         @Override
         public Object answer(InvocationOnMock invocation) {
            RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
            argument.sucesso(Arrays.asList(
               new Leilao("A"),
               new Leilao("B")
            ));
            return null;
         }
      }).when(cliente).todos(ArgumentMatchers.any(RespostaListener.class));

      ListaLeilaoActivity listaLeilaoActivity = new ListaLeilaoActivity();
      listaLeilaoActivity.buscaLeiloesNaAPIWeb(adapter, cliente);

      assertThat(adapter.getItemCount(), is(equalTo(2)));
   }
}