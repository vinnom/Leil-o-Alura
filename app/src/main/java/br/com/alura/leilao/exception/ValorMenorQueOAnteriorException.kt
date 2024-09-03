package br.com.alura.leilao.exception

class ValorMenorQueOAnteriorException(
    msg: String = "Lance precisa ser maior que o último lance"
) : RuntimeException(msg)