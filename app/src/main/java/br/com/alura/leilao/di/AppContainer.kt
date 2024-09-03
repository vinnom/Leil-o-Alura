package br.com.alura.leilao.di

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class AppContainer {

    private val supervisorJob = SupervisorJob()

    val webClient by lazy { LeilaoWebClient() }
    val uiScope = CoroutineScope(Dispatchers.Main + supervisorJob)
    val ioScope = CoroutineScope(Dispatchers.IO + supervisorJob)
}