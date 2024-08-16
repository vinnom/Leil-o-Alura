package br.com.alura.leilao.ui.activity

import br.com.alura.leilao.model.Leilao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.junit.Assert.assertThrows

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LeilaoActivityTest {

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun whenInstantiateLeilao_ThenCheckLeilaoName() = runBlockingTest {
        val leilao = Leilao(nome = "Leilao Teste")
        assertThat(leilao.nome, equalTo("Leilao Teste"))
    }

    @Test
    fun whenInstantiateLeilao_ThenCheckLeilaoValue() = runBlockingTest {
        val leilao = Leilao(nome = "Leilao Teste", valorInicial = 100.0)
        assertThat(leilao.valorInicial, equalTo(100.0))
    }

    @Test
    fun whenInstantiateLeilaoWithNegativeValue_ThenThrowsIllegalArgumentException() = runBlockingTest {
        assertThrows(IllegalArgumentException::class.java) {
            Leilao(nome = "Leilao Teste", valorInicial = -100.0)
        }
    }
}