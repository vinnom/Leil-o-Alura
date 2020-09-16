package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.alura.leilao.R;
import br.com.alura.leilao.api.EnviadorDeLance;
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;
import br.com.alura.leilao.ui.dialog.NovoLanceDialog;

import static br.com.alura.leilao.ui.activity.LeilaoConstantes.CHAVE_LEILAO;

public class LancesLeilaoActivity extends AppCompatActivity{

   private static final String TITULO_APPBAR = "Lances do leilão";
   private final LeilaoWebClient client = new LeilaoWebClient();
   private final AvisoDialogManager dialogManager = new AvisoDialogManager(this);
   private TextView maioresLances;
   private TextView menorLance;
   private TextView maiorLance;
   private TextView descricao;
   private UsuarioDAO dao;
   private Leilao leilaoRecebido;

   @Override
   protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lances_leilao);
      getSupportActionBar().setTitle(TITULO_APPBAR);
      carregaLeilaoRecebido();
   }

   private void carregaLeilaoRecebido(){
      Intent dadosRecebidos = getIntent();
      if(dadosRecebidos.hasExtra(CHAVE_LEILAO)){
         leilaoRecebido = (Leilao) dadosRecebidos.getSerializableExtra(CHAVE_LEILAO);
         inicializaAtributos();
         inicializaViews();
         apresentaDados();
         configuraFab();
      }
      else{
         finish();
      }
   }

   private void inicializaViews(){
      descricao = findViewById(R.id.lances_leilao_descricao);
      maiorLance = findViewById(R.id.lances_leilao_maior_lance);
      menorLance = findViewById(R.id.lances_leilao_menor_lance);
      maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
   }

   private void inicializaAtributos(){
      dao = new UsuarioDAO(this);
   }

   private void configuraFab(){
      FloatingActionButton fabAdiciona = findViewById(R.id.lances_leilao_fab_adiciona);
      fabAdiciona.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View view){
            mostraDialogNovoLance();
         }
      });
   }

   private void mostraDialogNovoLance(){
      NovoLanceDialog dialog = new NovoLanceDialog(
         this,
         new NovoLanceDialog.LanceCriadoListener(){
            @Override
            public void lanceCriado(Lance lance){
               enviaLance(lance);
            }
         },
         dao, dialogManager);
      dialog.mostra();
   }

   private void enviaLance(Lance lance){
      EnviadorDeLance enviador = new EnviadorDeLance(
         client,
         lanceProcessadoListener(),
         dialogManager);
      enviador.envia(leilaoRecebido, lance);
   }

   @NonNull
   private EnviadorDeLance.LanceProcessadoListener lanceProcessadoListener(){
      return new EnviadorDeLance.LanceProcessadoListener(){
         @Override
         public void processado(Leilao leilao){
            leilaoRecebido = leilao;
            apresentaDados();
         }
      };
   }

   private void apresentaDados(){
      adicionaDescricao(leilaoRecebido);
      adicionaMaiorLance(leilaoRecebido);
      adicionaMenorLance(leilaoRecebido);
      adicionaMaioresLances(leilaoRecebido);
   }

   private void adicionaMaioresLances(Leilao leilao){
      StringBuilder stringBuilder = new StringBuilder();
      if(leilao.getLances().get(0).getValor() != 0.0){
         for(Lance lance : leilao.getTresMaioresLances()){
            stringBuilder.append("- ");
            stringBuilder.append(lance.getValorFormatado());
            stringBuilder.append("\n");
         }
      }
      String maioresLancesEmTexto = stringBuilder.toString();
      maioresLances.setText(maioresLancesEmTexto);
   }

   private void adicionaMenorLance(Leilao leilao){
      menorLance.setText(leilao.getMenorLanceFormatado());
   }

   private void adicionaMaiorLance(Leilao leilao){
      maiorLance.setText(leilao.getMaiorLanceFormatado());
   }

   private void adicionaDescricao(Leilao leilao){
      descricao.setText(leilao.getDescricao());
   }
}
