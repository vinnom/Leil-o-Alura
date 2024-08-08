package br.com.alura.leilao.api.retrofit;

import br.com.alura.leilao.api.retrofit.service.ServiceDeTeste;

public class RetrofitDeTeste extends RetrofitInicializador {

    public ServiceDeTeste getServiceDeTeste() {
        return retrofit.create(ServiceDeTeste.class);
    }

}
