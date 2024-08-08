package br.com.alura.leilao;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static br.com.alura.leilao.model.formatter.FormatadorDeMoeda.formata;

public class ListaLeilaoTelaMatcher {

    public static Matcher<? super View> apareceLeilaoComDescricaoEMaiorValor(
            final int posicao,
            final String descricao,
            final double valor) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Descrição: ").appendValue(descricao).
                        appendText(", na posição: ").appendValue(posicao).
                        appendText(", com o valor: ").appendValue(formata(valor)).
                        appendText(" e ").
                        appendDescriptionOf(isDisplayed());
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                final RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(posicao);
                if (viewHolder == null) {
                    throw new IndexOutOfBoundsException("Não há ViewHolder na posição " + posicao);
                }
                final View viewAtual = viewHolder.itemView;

                int posicaoReal = viewHolder.getAdapterPosition();
                TextView tvDescricao = viewAtual.findViewById(R.id.item_leilao_descricao);
                String descricaoReal = tvDescricao.getText().toString();
                TextView tvMaiorLance = viewAtual.findViewById(R.id.item_leilao_maior_lance);
                String maiorLanceReal = tvMaiorLance.getText().toString();

                boolean estaNaPosicaoEsperada = (posicaoReal == posicao);
                boolean temDescricaoEsperada = (descricaoReal.equals(descricao)) &&
                        (isDisplayed().matches(tvDescricao));
                boolean temMaiorLanceEsperado = (maiorLanceReal.equals(formata(valor))) &&
                        (isDisplayed().matches(tvMaiorLance));


                return estaNaPosicaoEsperada
                        && temDescricaoEsperada
                        && temMaiorLanceEsperado
                        && isDisplayed().matches(viewAtual);
            }
        };
    }
}
