package br.com.alura.leilao.ui.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Leilao;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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

      onView(withId(R.id.lances_leilao_fab_adiciona))
         .perform(scrollTo(), click());

      onView(allOf(withId(R.id.alertTitle),
                   isDisplayed()))
         .check(matches(withText("Usuários não encontrados")));

      onView(allOf(withId(android.R.id.message),
                   isDisplayed()))
         .check(matches(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance.")));

      onView(allOf(withId(android.R.id.button1),
                   withText("Cadastrar usuário")))
         .perform(scrollTo(), click());

      onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                   isDisplayed()))
         .perform(click());

      onView(
         allOf(withId(R.id.form_usuario_nome_edittext),
               isDisplayed()))
         .perform(replaceText("Usuário"), closeSoftKeyboard());

      onView(allOf(withId(android.R.id.button1),
                   withText("Adicionar")))
         .perform(scrollTo(), click());
   }

   @After
   public void teardown() throws IOException{
      resetaBDAPIWeb();
      appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
   }
}
