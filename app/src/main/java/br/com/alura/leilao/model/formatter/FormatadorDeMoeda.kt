package br.com.alura.leilao.model.formatter

import java.text.Normalizer
import java.text.NumberFormat
import java.util.Locale

object FormatadorDeMoeda {

    private val BRASIL = Locale("pt", "BR")

    fun formata(valor: Double): String {
        val valorFormatado = NumberFormat.getCurrencyInstance(BRASIL).format(valor)
        val valorNormalizado = Normalizer.normalize(valorFormatado, Normalizer.Form.NFKC)
        return valorNormalizado
    }
}