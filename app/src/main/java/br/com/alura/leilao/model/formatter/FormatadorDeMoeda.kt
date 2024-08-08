package br.com.alura.leilao.model.formatter

import java.text.NumberFormat
import java.util.Locale

object FormatadorDeMoeda {

    private val BRASIL = Locale("pt", "BR")

    fun formata(valor: Double): String {
        val formatador = NumberFormat.getCurrencyInstance(BRASIL)
        return formatador.format(valor)
    }
}