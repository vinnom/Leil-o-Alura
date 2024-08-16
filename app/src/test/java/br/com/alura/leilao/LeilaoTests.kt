package br.com.alura.leilao.model

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.MockitoAnnotations

class LeilaoTest {

    private lateinit var leilao: Leilao

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        leilao = Leilao(descricao = "Leilao de teste")
    }

    @Test
    fun `when Propoe Lance, then Add Lance`() = runBlockingTest {
        val lance = Lance(Usuario(nome = "Usuario 1"), 100.0)

        leilao.propoe(lance)

        assertThat(leilao.lances[0], equalTo(lance))
    }

    @Test
    fun `when Propoe Lance From Same User, then Throw Exception`() = runBlockingTest {
        val lance1 = Lance(Usuario(nome = "Usuario 1"), 100.0)
        val lance2 = Lance(Usuario(nome = "Usuario 1"), 200.0)

        leilao.propoe(lance1)

        assertThrows<UsuarioDeuLancesSeguidosException> {
            leilao.propoe(lance2)
        }
    }

    @Test
    fun `when Propoe Lance Lower Than Previous, then Throw Exception`() = runBlockingTest {
        val lance1 = Lance(Usuario(nome = "Usuario 1"), 100.0)
        val lance2 = Lance(Usuario(nome = "Usuario 2"), 50.0)

        leilao.propoe(lance1)

        assertThrows<ValorMenorQueOAnteriorException> {
            leilao.propoe(lance2)
        }
    }

    @Test
    fun `when Propoe More Than Five Lances From Same User, then Throw Exception`() = runBlockingTest {
        val usuario = Usuario(nome = "Usuario 1")
        val lance1 = Lance(usuario, 100.0)
        val lance2 = Lance(usuario, 200.0)
        val lance3 = Lance(usuario, 300.0)
        val lance4 = Lance(usuario, 400.0)
        val lance5 = Lance(usuario, 500.0)
        val lance6 = Lance(usuario, 600.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)
        leilao.propoe(lance3)
        leilao.propoe(lance4)
        leilao.propoe(lance5)

        assertThrows<QuantidadeMaximaDeLancesException> {
            leilao.propoe(lance6)
        }
    }

    @Test
    fun `when Get Maior Lance, then Return Maior Lance`() = runBlockingTest {
        val lance1 = Lance(Usuario(nome = "Usuario 1"), 100.0)
        val lance2 = Lance(Usuario(nome = "Usuario 2"), 200.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)

        val maiorLance = leilao.getMaiorLance()

        assertThat(maiorLance, equalTo(lance2))
    }

    @Test
    fun `when Get Menor Lance, then Return Menor Lance`() = runBlockingTest {
        val lance1 = Lance(Usuario(nome = "Usuario 1"), 100.0)
        val lance2 = Lance(Usuario(nome = "Usuario 2"), 200.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)

        val menorLance = leilao.getMenorLance()

        assertThat(menorLance, equalTo(lance1))
    }

    @Test
    fun `when Get Tres Maiores Lances, then Return Tres Maiores Lances`() = runBlockingTest {
        val lance1 = Lance(Usuario(nome = "Usuario 1"), 100.0)
        val lance2 = Lance(Usuario(nome = "Usuario 2"), 200.0)
        val lance3 = Lance(Usuario(nome = "Usuario 3"), 300.0)
        val lance4 = Lance(Usuario(nome = "Usuario 4"), 400.0)

        leilao.propoe(lance1)
        leilao.propoe(lance2)
        leilao.propoe(lance3)
        leilao.propoe(lance4)

        val tresMaioresLances = leilao.getTresMaioresLances()

        assertThat(tresMaioresLances, equalTo(listOf(lance4, lance3, lance2)))
    }
}