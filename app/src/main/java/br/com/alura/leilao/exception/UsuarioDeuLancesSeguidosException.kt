package br.com.alura.leilao.exception

class UsuarioDeuLancesSeguidosException(
    msg: String = "O mesmo usuário do último lance não pode propror novos lances"
) : RuntimeException(msg)