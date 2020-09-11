package br.com.alura.leilao.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Lance implements Serializable, Comparable {

   private static final Locale BRASIL = new Locale("pt", "BR");
   private final Usuario usuario;
   private final double valor;
   private final String valorFormatado;

   public Lance(Usuario usuario, double valor) {
      this.usuario = usuario;
      this.valor = valor;
      this.valorFormatado = formata(valor);
   }

   public double getValor() {
      return valor;
   }

   public String getValorFormatado() {
      if(valorFormatado != null) {
         return valorFormatado;
      }
      return "";
   }

   @Override
   public int compareTo(@NonNull Object o) {
      Lance lance = (Lance) o;
      if(this.getValor() > lance.getValor()) {
         return -1;
      }
      if(this.getValor() < lance.getValor()) {
         return 1;
      }
      return 0;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   @Override
   public int hashCode() {
      int result;
      long temp;
      result = usuario.hashCode();
      temp = Double.doubleToLongBits(valor);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      return result;
   }

   @Override
   public boolean equals(Object o) {
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      Lance lance = (Lance) o;

      if(Double.compare(lance.valor, valor) != 0) return false;
      return usuario.equals(lance.usuario);
   }

   public String formata(double valor) {
      NumberFormat formatador = NumberFormat.getCurrencyInstance(Lance.BRASIL);
      return formatador.format(valor);
   }
}
