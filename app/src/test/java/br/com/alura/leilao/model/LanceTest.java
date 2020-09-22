package br.com.alura.leilao.model;

import org.junit.Test;

import static br.com.alura.leilao.model.formatter.FormatadorDeMoeda.formata;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LanceTest {

   @Test
   public void deve_RetornarValorFormatadoEmPTBR() {
      Lance lance = new Lance(new Usuario("usuario"), 100.0);
      String valorEsperado = formata(100.0);

      assertThat(lance.getValorFormatado(), is(equalTo(valorEsperado)));
   }
}