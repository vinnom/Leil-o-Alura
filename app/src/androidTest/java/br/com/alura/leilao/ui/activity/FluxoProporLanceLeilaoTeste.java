package br.com.alura.leilao.ui.activity;


import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;
import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static br.com.alura.leilao.model.formatter.FormatadorDeMoeda.formata;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
public class FluxoProporLanceLeilaoTeste extends BaseTesteIntegracao {

    @Before
    public void setup() throws IOException {
        resetaBDAPIWeb();
        appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
    }

    @Test
    public void deve_FazerOFluxoCompletoParaAdicionarUmLance_AdicionandoUmUsuario() throws IOException {
        tentaSalvarLeilaoNaAPIWeb(new Leilao("A"));

        launch(ListaLeilaoActivity.class);

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),
                isDisplayed()))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.alertTitle),
                isDisplayed()))
                .check(matches(withText("Usuários não encontrados")));

        onView(allOf(withId(android.R.id.message),
                isDisplayed()))
                .check(matches(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance.")));

        onView(allOf(withId(android.R.id.button1),
                withText("Cadastrar usuário"),
                isDisplayed()))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.form_usuario_nome_edittext),
                isDisplayed()))
                .perform(typeText("Usuario"), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1),
                withText("Adicionar"),
                isDisplayed()))
                .perform(scrollTo(), click());

        pressBack();

        verificaFluxoDeProporLances("100", 1, "Usuario");

        String lanceFormatado = formata(100.0);

        onView(allOf(withId(R.id.lances_leilao_maior_lance),
                isDisplayed()))
                .check(matches(withText(lanceFormatado)));

        onView(allOf(withId(R.id.lances_leilao_menor_lance),
                isDisplayed()))
                .check(matches(withText(lanceFormatado)));

        onView(allOf(withId(R.id.lances_leilao_maiores_lances),
                isDisplayed()))
                .check(matches(withText("- " + lanceFormatado + " - (1) Usuario" + "\n")));

    }

    @Test
    public void deve_AdicionarTresLances_QuandoDoisUsuariosJaExistiremNoBD() throws IOException {
        tentaSalvarLeilaoNaAPIWeb(new Leilao("A"));
        tentaSalvarUsuariosNoBD(new Usuario("Usuario1"),
                new Usuario(("Usuario2")));

        launch(ListaLeilaoActivity.class);

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        verificaFluxoDeProporLances("100", 1, "Usuario1");
        verificaFluxoDeProporLances("200", 2, "Usuario2");
        verificaFluxoDeProporLances("300", 1, "Usuario1");

        String lanceFormatado1 = formata(100.0);
        String lanceFormatado2 = formata(200.0);
        String lanceFormatado3 = formata(300.0);

        onView(allOf(withId(R.id.lances_leilao_maior_lance),
                isDisplayed()))
                .check(matches(withText(lanceFormatado3)));

        onView(allOf(withId(R.id.lances_leilao_menor_lance),
                isDisplayed()))
                .check(matches(withText(lanceFormatado1)));

        String maioresLances = formataStringDeMaioresLances(lanceFormatado1, lanceFormatado2, lanceFormatado3);

        onView(allOf(withId(R.id.lances_leilao_maiores_lances),
                isDisplayed()))
                .check(matches((withText(maioresLances))));

    }

    @After
    public void teardown() throws IOException {
        resetaBDAPIWeb();
        appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
    }

    private void tentaSalvarUsuariosNoBD(Usuario... usuarios) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(appContext);

        for (Usuario usuario : usuarios) {
            usuarioDAO.salva(usuario);
            if (usuario == null) {
                Assert.fail(usuario.getNome() + " não foi salvo corretamente");
            }
        }
    }

    private void verificaFluxoDeProporLances(String valorLance, int idUsuario, String nomeUsuario) {
        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),
                isDisplayed()))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.alertTitle),
                isDisplayed()))
                .check(matches(withText("Novo lance")));

        onView(allOf(withId(R.id.form_lance_valor_edittext),
                isDisplayed()))
                .perform(typeText(valorLance));

        onView(allOf(withId(R.id.form_lance_usuario),
                isDisplayed()))
                .perform(click());

        onData(is(new Usuario(idUsuario, nomeUsuario)))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(allOf(withId(android.R.id.button1),
                withText("Propor"),
                isDisplayed()))
                .perform(scrollTo(), click());
    }

    private String formataStringDeMaioresLances(String lanceFormatado1, String lanceFormatado2, String lanceFormatado3) {
        return new StringBuilder()
                .append("- ").append(lanceFormatado3).append(" - (1) Usuario1").append("\n")
                .append("- ").append(lanceFormatado2).append(" - (2) Usuario2").append("\n")
                .append("- ").append(lanceFormatado1).append(" - (1) Usuario1").append("\n")
                .toString();
    }
}
