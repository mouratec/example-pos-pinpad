package br.com.auttar.sampleauttarsdk.di

import android.app.Application
import br.com.auttar.sdk.Auttar
import br.com.auttar.sdk.interfaces.IAuttarConfigSDK
import br.com.auttar.sdk.interfaces.IAuttarSDK
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.dsl.module

class AuttarSdkModule {

    fun provider(application: Application) = module {
        // As instâncias já foram inicializadas no SampleApplication.onCreate()
        single<IAuttarSDK> { Auttar.getSDK() }
        single<IAuttarConfigSDK> { Auttar.getConfigSDK() }
    }
}