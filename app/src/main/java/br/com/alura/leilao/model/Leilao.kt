package br.com.alura.leilao.model

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import java.io.Serializable
import kotlin.jvm.Throws

data class Leilao(
    val id: Long = 0L,
    val descricao: String,
    val lances: MutableList<Lance> = mutableListOf(),
) : Serializable {

    init {
        if (lances.isEmpty()) {
            lances.add(Lance(Usuario(nome = ""), 0.0))
        }
    }

    val maiorLanceFormatado: String
        get() {
            return getMaiorLance().valorFormatado
        }
    val menorLanceFormatado: String
        get() {
            return getMenorLance().valorFormatado
        }
    val tresMaioresLances: List<Lance>
        get() {
            return when {
                lances.isEmpty() -> lances
                lances.size < 3 -> lances.subList(0, lances.size)
                else -> lances.subList(0, 3)
            }
        }

    fun propoe(lance: Lance) {
        if (lances[0].valor == 0.0) {
            lances[0] = lance
        } else {
            valida(lance)
            lances.add(0, lance)
        }
    }

    private fun valida(lance: Lance) {
        validaLanceSemRepeticaoDoUsuario(lance)
        validaQuantidadeMaximaDeLancesDeUmMesmoUsuario(lance)
        validaValorMaiorQueAnterior(lance)
    }

    private fun validaLanceSemRepeticaoDoUsuario(lance: Lance) {
        if (lance.usuario == lances[0].usuario) {
            throw UsuarioDeuLancesSeguidosException()
        }
    }

    private fun validaQuantidadeMaximaDeLancesDeUmMesmoUsuario(lance: Lance) {
        val contador = lances.count { it.usuario == lance.usuario }
        if (contador == 5) {
            throw QuantidadeMaximaDeLancesException()
        }
    }

    private fun validaValorMaiorQueAnterior(lance: Lance) {
        if (lance.valor < lances[0].valor) {
            throw ValorMenorQueOAnteriorException()
        }
    }

    private fun getMaiorLance(): Lance {
        return lances[0]
    }

    private fun getMenorLance(): Lance {
        return lances[lances.size - 1]
    }


}