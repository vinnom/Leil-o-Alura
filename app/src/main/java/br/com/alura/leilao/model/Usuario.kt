package br.com.alura.leilao.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
) : Serializable {

    override fun toString(): String {
        return "($id) $nome"
    }
}