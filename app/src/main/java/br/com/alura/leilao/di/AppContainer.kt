package br.com.alura.leilao.di

import android.content.Context
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient
import br.com.alura.leilao.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class AppContainer(context: Context) {

    private val supervisorJob = SupervisorJob()

    val webClient by lazy { LeilaoWebClient() }
    val roomDao by lazy { AppDatabase.getDatabase(context).usuarioDao() }
    val uiScope = CoroutineScope(Dispatchers.Main + supervisorJob)
    val ioScope = CoroutineScope(Dispatchers.IO + supervisorJob)
}