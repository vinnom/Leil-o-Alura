package br.com.alura.leilao

import android.app.Application
import br.com.alura.leilao.di.AppContainer

class LeilaoAluraApp : Application() {

    val appContainer by lazy { AppContainer(context = this) }
}