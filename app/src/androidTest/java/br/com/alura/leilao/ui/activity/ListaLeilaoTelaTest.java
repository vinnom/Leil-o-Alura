package br.com.alura.leilao.ui.activity;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Leilao;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static br.com.alura.leilao.ListaLeilaoTelaMatcher.apareceLeilaoComDescricaoEMaiorValor;

public class ListaLeilaoTelaTest extends BaseTesteIntegracao {

    @Before
    public void setup() throws IOException {
        resetaBDAPIWeb();
    }

    @Test
    public void deve_MostrarUmaView_QuandoBuscaUmLeilaoNaAPIWeb() throws IOException {
        tentaSalvarLeilaoNaAPIWeb(new Leilao("A"));

        launch(ListaLeilaoActivity.class);
        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoComDescricaoEMaiorValor(0, "A", 0.0)));
    }

    @Test
    public void deve_MostrarDuasViews_QuandoBuscaDoisLeiloesNaAPIWeb() throws IOException {
        tentaSalvarLeilaoNaAPIWeb(new Leilao("A"), new Leilao("AB"));

        launch(ListaLeilaoActivity.class);
        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoComDescricaoEMaiorValor(0, "A", 0.0)));
        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoComDescricaoEMaiorValor(1, "AB", 0.0)));
    }

    @Test
    public void deve_MostrarUltimaView_QuandoBuscaOitLeiloesNaAPIWeb() throws IOException {
        tentaSalvarLeilaoNaAPIWeb(
                new Leilao("A"),
                new Leilao("AB"),
                new Leilao("ABC"),
                new Leilao("ABCD"),
                new Leilao("ABCDE"),
                new Leilao("ABCDEF"),
                new Leilao("ABCDEFG"),
                new Leilao("ABCDEFGH"));

        launch(ListaLeilaoActivity.class);

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(7))
                .check(matches(apareceLeilaoComDescricaoEMaiorValor(7, "ABCDEFGH", 0.0)));
    }

    @After
    public void teardown() throws IOException {
        resetaBDAPIWeb();
    }
}