package br.com.alura.leilao.api.retrofit.service;

import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceDeTeste {

    @POST("leilao")
    Call<Leilao> salva(@Body String descricao);

    @GET("reset")
    Call<Void> reseta();

}
