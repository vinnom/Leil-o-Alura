package br.com.alura.leilao;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.WebClienteDeTeste;
import br.com.alura.leilao.model.Leilao;

public class BaseTesteIntegracao{

   private static final String FALHA_LIMPEZA_BD_API_WEB = "Não foi possível resetar o banco de dados";
   private static final String FALHA_AO_SALVAR_LEILAO = "Não foi possível salvar o leilão";
   private final WebClienteDeTeste cliente = new WebClienteDeTeste();
   protected final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

   protected void resetaBDAPIWeb() throws IOException{
      boolean resetado = cliente.reseta();
      if(!resetado){
         Assert.fail(FALHA_LIMPEZA_BD_API_WEB);
      }
   }

   protected void tentaSalvarLeilaoNaAPIWeb(Leilao... leiloes) throws IOException{
      for(Leilao l : leiloes){
         Leilao leilao = cliente.salva(l);
         if(leilao == null){
            Assert.fail(FALHA_AO_SALVAR_LEILAO);
         }
      }
   }
}
