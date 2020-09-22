package br.com.alura.leilao.api.retrofit.client;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.RetrofitDeTeste;
import br.com.alura.leilao.api.retrofit.service.ServiceDeTeste;
import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.Response;

public class WebClienteDeTeste extends WebCliente{

   private final ServiceDeTeste service;

   public WebClienteDeTeste(){
      this.service = new RetrofitDeTeste().getServiceDeTeste();
   }

   public Leilao salva(Leilao leilao) throws IOException{
      Call<Leilao> call = service.salva(leilao.getDescricao());
      Response<Leilao> resposta = call.execute();
      if(temDados(resposta)){
         return resposta.body();
      }
      return null;
   }

   public boolean reseta() throws IOException{
      Call<Void> call = service.reseta();
      return call.execute().isSuccessful();
   }
}
