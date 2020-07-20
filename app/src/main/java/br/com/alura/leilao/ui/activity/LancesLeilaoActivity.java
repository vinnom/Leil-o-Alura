package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());
            TextView campoMaiorLance = findViewById(R.id.lances_leilao_maior_lance);
            campoMaiorLance.setText(leilao.getMaiorLanceFormatado());
            TextView campoMenorLance = findViewById(R.id.lances_leilao_menor_lance);
            campoMenorLance.setText(leilao.getMenorLanceFormatado());
            TextView campoMaioresLances = findViewById(R.id.lances_leilao_maiores_lances);
            StringBuilder stringBuilder = new StringBuilder();
            for(Lance lance : leilao.getTresMaioresLances()) {
                stringBuilder.append(lance.getValorFormatado() + "\n");
            }
            campoMaioresLances.setText(stringBuilder.toString());
        }
    }
}
