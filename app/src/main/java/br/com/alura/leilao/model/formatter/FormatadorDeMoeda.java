package br.com.alura.leilao.model.formatter;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.alura.leilao.model.Lance;

public class FormatadorDeMoeda{

   private static final Locale BRASIL = new Locale("pt", "BR");

   public static String formata(double valor) {
      NumberFormat formatador = NumberFormat.getCurrencyInstance(BRASIL);
      return formatador.format(valor);
   }
}
