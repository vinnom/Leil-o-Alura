package br.com.alura.leilao.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException;
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException;
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest{

   @Mock
   private Leilao leilao;
   @Mock
   private AvisoDialogManager dialogManager;
   @Mock
   private LeilaoWebClient cliente;
   @Mock
   private EnviadorDeLance.LanceProcessadoListener listener;

   @Test
   public void deve_MostrarDialogoLanceMenor_QuandoValorDoLanceForMenorQueOAnterior(){
      doThrow(ValorMenorQueOAnteriorException.class).when(leilao)
         .propoe(any(Lance.class));

      EnviadorDeLance enviadorDeLance = new EnviadorDeLance(cliente, listener, dialogManager);
      enviadorDeLance.envia(leilao, new Lance(new Usuario("A"), 100));

      verify(dialogManager).mostraAvisoLanceMenorQueUltimoLance();
      verify(cliente, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
   }

   @Test
   public void deve_MostrarDialogoLancesSeguidos_QuandoUsuarioDerLancesSeguidos(){
      doThrow(UsuarioDeuLancesSeguidosException.class).when(leilao)
         .propoe(any(Lance.class));

      EnviadorDeLance enviadorDeLance = new EnviadorDeLance(cliente, listener, dialogManager);
      enviadorDeLance.envia(leilao, new Lance(new Usuario("A"), 100));

      verify(dialogManager).mostraAvisoLanceSeguidoDoMesmoUsuario();
      verify(cliente, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
   }

   @Test
   public void deve_MostrarDialogoLimiteLances_QuandoUsuarioAtingirLimiteDeCincoLances(){
      doThrow(QuantidadeMaximaDeLancesException.class).when(leilao)
         .propoe(any(Lance.class));

      EnviadorDeLance enviadorDeLance = new EnviadorDeLance(cliente, listener, dialogManager);
      enviadorDeLance.envia(leilao, new Lance(new Usuario("A"), 100));

      verify(dialogManager).mostraAvisoUsuarioJaDeuCincoLances();
      verify(cliente, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
   }

   @Test
   public void deve_MostrarDialogoFalha_QuandoTiverUmaFalhaNoEnvio(){
      doAnswer(new Answer(){
         @Override
         public Object answer(InvocationOnMock invocation){
            RespostaListener<Void> argument = invocation.getArgument(2);
            argument.falha("");
            return null;
         }
      }).when(cliente).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

      EnviadorDeLance enviadorDeLance = new EnviadorDeLance(cliente, listener, dialogManager);
      enviadorDeLance.envia(leilao, mock(Lance.class));

      verify(dialogManager).mostraToastFalhaNoEnvio();
   }

   @Test
   public void deve_ObterSucessoNoEnvioDeLance(){
      doAnswer(new Answer(){
         @Override
         public Object answer(InvocationOnMock invocation){
            RespostaListener<Void> argument = invocation.getArgument(2);
            argument.sucesso(any(Void.class));
            return null;
         }
      }).when(cliente).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

      EnviadorDeLance enviadorDeLance = new EnviadorDeLance(cliente, listener, dialogManager);
      enviadorDeLance.envia(leilao, mock(Lance.class));

      verify(listener).processado(any(Leilao.class));
   }
}