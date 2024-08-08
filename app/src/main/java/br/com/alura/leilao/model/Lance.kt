package br.com.alura.leilao.model

import br.com.alura.leilao.model.formatter.FormatadorDeMoeda.formata
import java.io.Serializable

data class Lance(
    val usuario: Usuario,
    val valor: Double,
) : Serializable, Comparable<Lance> {

    val valorFormatado: String = formata(valor)

    override fun compareTo(other: Lance): Int {
        return when {
            this.valor > other.valor -> -1
            this.valor < other.valor -> 1
            else -> 0
        }
    }
}