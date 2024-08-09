package br.com.alura.leilao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.leilao.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    fun salva(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios")
    fun todos(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun buscaPorId(id: Long): Usuario?
}
