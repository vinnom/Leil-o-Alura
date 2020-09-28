package br.com.alura.leilao.ui.activity;


import android.content.Context;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
public class FluxoAdicaoUsuarioTeste{

   private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

   @Before
   public void setup(){
      appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
   }

   @Test
   public void deve_AdicionarUsuarioE_MostrarViewComIdENome(){
      launch(ListaLeilaoActivity.class);

      onView(
         allOf(withId(R.id.lista_leilao_menu_usuarios),
               withContentDescription("Usuários"),
               isDescendantOfA(withId(R.id.action_bar)),
               isDisplayed()))
         .perform(click());

      onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                   isDisplayed()))
         .perform(click());

      onView(
         allOf(withId(R.id.form_usuario_nome_edittext),
               isDisplayed()))
         .perform(replaceText("Usuário"), closeSoftKeyboard());

      onView(
         allOf(withId(android.R.id.button1),
               withText("Adicionar"),
               isDisplayed()))
         .perform(scrollTo(), click());

      onView(
         allOf(withId(R.id.item_usuario_id_com_nome),
               isDisplayed()))
         .check(matches(withText("(1) Usuário")));
   }

   @After
   public void tearDown(){
      appContext.deleteDatabase(BuildConfig.BANCO_DADOS);
   }
}
