package br.com.alura.leilao.ui.activity;

import org.junit.Test;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ListaLeilaoTelaTest{

   @Test
   public void deve_CarregarView_QuandoBuscaNaAPIWeb(){
      launch(ListaLeilaoActivity.class);
      onView(withText("Carro")).check(matches(isDisplayed()));
   }

}