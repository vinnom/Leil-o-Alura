package br.com.alura.leilao.model

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class LeilaoTest {

    private val leilao = Leilao(descricao = "Leilao de Teste")
    private val usuario1 = Usuario(nome = "User 1")
    private val usuario2 = Usuario(nome = "User 2")

    @Test
    fun `should add a bid when there are no bids`() {
        leilao.propoe(Lance(usuario1, 100.0))
        Assert.assertThat(leilao.lances.size, CoreMatchers.equalTo(1))
        Assert.assertThat(leilao.getMaiorLance().valor, CoreMatchers.equalTo(100.0))
    }

    @Test
    fun `should add a bid when last user is different`() {
        leilao.propoe(Lance(usuario1, 100.0))
        leilao.propoe(Lance(usuario2, 200.0))
        Assert.assertThat(leilao.lances.size, CoreMatchers.equalTo(2))
        Assert.assertThat(leilao.getMaiorLance().valor, CoreMatchers.equalTo(200.0))
    }

    @Test(expected = UsuarioDeuLancesSeguidosException::class)
    fun `should not add a bid when last user is the same`() {
        leilao.propoe(Lance(usuario1, 100.0))
        leilao.propoe(Lance(usuario1, 200.0))
    }

    @Test(expected = ValorMenorQueOAnteriorException::class)
    fun `should not add a bid when value is lower than the last bid`() {
        leilao.propoe(Lance(usuario1, 200.0))
        leilao.propoe(Lance(usuario2, 100.0))
    }

    @Test(expected = QuantidadeMaximaDeLancesException::class)
    fun `should not add a bid when user already made 5 bids`() {
        for (i in 1..5) {
            leilao.propoe(Lance(usuario1, i * 100.0))
        }
        leilao.propoe(Lance(usuario1, 600.0))
    }

    @Test
    fun `should return the three highest bids`() {
        for (i in 1..5) {
            leilao.propoe(Lance(usuario1, i * 100.0))
        }
        val tresMaioresLances = leilao.getTresMaioresLances()
        Assert.assertThat(tresMaioresLances.size, CoreMatchers.equalTo(3))
        Assert.assertThat(tresMaioresLances[0].valor, CoreMatchers.equalTo(500.0))
        Assert.assertThat(tresMaioresLances[1].valor, CoreMatchers.equalTo(400.0))
        Assert.assertThat(tresMaioresLances[2].valor, CoreMatchers.equalTo(300.0))
    }
}