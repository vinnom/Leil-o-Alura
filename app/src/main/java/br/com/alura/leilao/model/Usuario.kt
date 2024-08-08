package br.com.alura.leilao.model

import java.io.Serializable

data class Usuario(
    val id: Long = 0L,
    val nome: String,
) : Serializable {

    override fun toString(): String {
        return "($id) $nome"
    }
}