package br.com.alura.leilao.api;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException;
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException;
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

public class EnviadorDeLance{

   private final LeilaoWebClient client;
   private final LanceProcessadoListener listener;
   private final AvisoDialogManager dialogManager;

   public EnviadorDeLance(
      LeilaoWebClient client,
      LanceProcessadoListener listener,
      AvisoDialogManager dialogManager)
   {
      this.client = client;
      this.listener = listener;
      this.dialogManager = dialogManager;
   }

   public void envia(final Leilao leilao, Lance lance){
      try{
         leilao.propoe(lance);
         client.propoe(lance, leilao.getId(), new RespostaListener<Void>(){
            @Override
            public void sucesso(Void resposta){
               listener.processado(leilao);
            }

            @Override
            public void falha(String mensagem){
               dialogManager.mostraToastFalhaNoEnvio();
            }
         });
      } catch(ValorMenorQueOAnteriorException exception){
         dialogManager.mostraAvisoLanceMenorQueUltimoLance();
      } catch(UsuarioDeuLancesSeguidosException exception){
         dialogManager.mostraAvisoLanceSeguidoDoMesmoUsuario();
      } catch(QuantidadeMaximaDeLancesException exception){
         dialogManager.mostraAvisoUsuarioJaDeuCincoLances();
      }
   }

   public interface LanceProcessadoListener{
      void processado(Leilao leilao);
   }

}
