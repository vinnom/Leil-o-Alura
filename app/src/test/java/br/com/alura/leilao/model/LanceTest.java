package br.com.alura.leilao.model;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LanceTest {

	@Test
	public void deve_RetornarValorFormatadoEmPTBR(){
		Lance lance = new Lance(new Usuario("usuario"), 100.0);
		NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		String valorEsperado = formatador.format(100.0);

		assertThat(lance.formata(100.0), is(equalTo(valorEsperado)));
	}
}