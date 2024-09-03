package br.com.alura.leilao.exception

class QuantidadeMaximaDeLancesException(
    msg: String = "Usuário não pode dar mais de cinco lances no mesmo leilão"
) : RuntimeException(msg)