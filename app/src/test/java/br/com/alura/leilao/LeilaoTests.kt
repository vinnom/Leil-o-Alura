package br.com.alura.leilao

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import br.com.alura.leilao.model.Lance
import br.com.alura.leilao.model.Leilao
import br.com.alura.leilao.model.Usuario
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Before
import org.junit.Test

class LeilaoTest {

    private lateinit var leilao: Leilao

    @Before
    fun setup() {
        leilao = Leilao(descricao = "Item de leilão")
    }

    @Test
    fun shouldAddLanceWhenThereAreNoLances() {
        val lance = Lance(Usuario(nome = "User"), 100.0)

        leilao.propoe(lance)

        assertThat(leilao.lances.size, equalTo(1))
        assertThat(leilao.lances[0].valor, equalTo(100.0))
    }

    @Test(expected = UsuarioDeuLancesSeguidosException::class)
    fun shouldNotAddLanceWhenSameUserMakesConsecutiveBids() {
        val user = Usuario(nome = "User")
        val lance1 = Lance(user, 100.0)
        val lance2 = Lance(user, 200.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)
    }

    @Test(expected = QuantidadeMaximaDeLancesException::class)
    fun shouldNotAddLanceWhenUserMakesMoreThanFiveBids() {
        val user1 = Usuario(nome = "User1")
        val user2 = Usuario(nome = "User2")
        val lance1 = Lance(user1, 100.0)
        val lance2 = Lance(user2, 200.0)
        val lance3 = Lance(user1, 300.0)
        val lance4 = Lance(user2, 400.0)
        val lance5 = Lance(user1, 500.0)
        val lance6 = Lance(user2, 600.0)
        val lance7 = Lance(user1, 700.0)
        val lance8 = Lance(user2, 800.0)
        val lance9 = Lance(user1, 900.0)
        val lance10 = Lance(user2, 1000.0)
        val lance11 = Lance(user1, 1100.0)
        leilao.propoe(lance1)
        leilao.propoe(lance2)
        leilao.propoe(lance3)
        leilao.propoe(lance4)
        leilao.propoe(lance5)
        leilao.propoe(lance6)
        leilao.propoe(lance7)
        leilao.propoe(lance8)
        leilao.propoe(lance9)
        leilao.propoe(lance10)
        leilao.propoe(lance11)
    }

    @Test(expected = ValorMenorQueOAnteriorException::class)
    fun shouldNotAddLanceWhenValueIsLowerThanLastBid() {
        val user1 = Usuario(nome = "User 1")
        val user2 = Usuario(nome = "User 2")
        val lance1 = Lance(user1, 100.0)
        val lance2 = Lance(user2, 50.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)
    }

    @Test
    fun shouldReturnThreeHighestBidsWhenThereAreMoreThanThreeBids() {
        val user1 = Usuario(nome = "User 1")
        val user2 = Usuario(nome = "User 2")
        val user3 = Usuario(nome = "User 3")
        val user4 = Usuario(nome = "User 4")
        val lance1 = Lance(user1, 100.0)
        val lance2 = Lance(user2, 200.0)
        val lance3 = Lance(user3, 300.0)
        val lance4 = Lance(user4, 400.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)
        leilao.propoe(lance3)
        leilao.propoe(lance4)

        val highestBids = leilao.tresMaioresLances

        assertThat(highestBids.size, equalTo(3))
        assertThat(highestBids[0].valor, equalTo(400.0))
        assertThat(highestBids[1].valor, equalTo(300.0))
        assertThat(highestBids[2].valor, equalTo(200.0))
    }
}