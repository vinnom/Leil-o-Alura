package br.com.alura.leilao.repository

import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario

class UsuarioRepository(private val dao: UsuarioDAO) {
    fun todos() = dao.todos()
    fun salva(usuario: Usuario) = dao.salva(usuario)
    fun buscaPorId(id: Long) = dao.buscaPorId(id)
}