package br.com.alura.leilao.ui.activity;


import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;
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
public class FluxoProporLanceLeilaoTeste extends BaseTesteIntegracao{

   @Before
   public void setup() throws IOException{
      resetaBDAPIWeb();
      appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
   }

   @Test
   public void fluxoProporLanceLeilaoTeste() throws IOException{
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

      onView(allOf(withId(R.id.lances_leilao_fab_adiciona),
                   isDisplayed()))
         .perform(scrollTo(), click());

      onView(allOf(withId(R.id.alertTitle),
                   isDisplayed()))
         .check(matches(withText("Novo lance")));

      onView(allOf(withId(R.id.form_lance_valor_edittext),
                   isDisplayed()))
         .perform(typeText("100"));

      onView(allOf(withId(R.id.form_lance_usuario),
                   isDisplayed()))
         .perform(click());

      onData(is(new Usuario(1, "Usuario")))
         .inRoot(RootMatchers.isPlatformPopup())
         .perform(click());

      onView(allOf(withId(android.R.id.button1),
                   withText("Propor"),
                   isDisplayed()))
         .perform(scrollTo(), click());

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

   @After
   public void teardown() throws IOException{
      resetaBDAPIWeb();
      appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
   }
}
