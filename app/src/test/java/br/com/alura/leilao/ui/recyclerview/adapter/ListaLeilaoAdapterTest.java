package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

   @Mock
   private Context contexto;
   @Spy
   private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(contexto);

   @Test
   public void deve_AtualizarLista_QuandoRecebeNovosLeiloes() {
      doNothing().when(adapter).notificaMudancaNoConjuntoDeDados();

      adapter.atualiza(Arrays.asList(
         new Leilao("A"),
         new Leilao("B")
      ));

      assertThat(adapter.getItemCount(), is(equalTo(2)));
   }

}