package br.com.auttar.sampleauttarsdk

import android.app.Application
import br.com.auttar.sampleauttarsdk.BuildConfig
import br.com.auttar.sampleauttarsdk.di.modulesAuttarKoin
import br.com.auttar.sampleauttarsdk.util.Brazil
import br.com.auttar.sdk.Auttar
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.Locale

class SampleApplication : Application() {

    companion object {
        private val _isSdkReady = kotlinx.coroutines.flow.MutableStateFlow(false)
        val isSdkReady: kotlinx.coroutines.flow.StateFlow<Boolean> = _isSdkReady
    }

    override fun onCreate() {
        super.onCreate()

        // 1. Configuração de Autenticação Global
        Authenticator.setDefault(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("hti", "h123456".toCharArray())
            }
        })

        // 2. Localização
        Locale.setDefault(Brazil.locale)

        // 3. Inicialização do Koin
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@SampleApplication)
            modules(modulesAuttarKoin(this@SampleApplication))
        }

        // 4. Inicialização Síncrona do Core
        Auttar.initHomolog(application = this)

        // 5. Configuração das Propriedades do SDK
        MainScope().launch(Dispatchers.IO) {
            try {
                val config = Auttar.getConfigSDK()
                
                // Configurações obrigatórias
                config.userConfiguration.setWorkDir(filesDir.absolutePath)
                config.userConfiguration.setAllowsPinpad(true)
                config.userConfiguration.setPinpadUSB(false)
                config.userConfiguration.setIsPinPadBluetooth(false)
                
                // Sinaliza que o SDK está pronto para uso
                _isSdkReady.value = true
            } catch (e: Exception) {
                // Em produção, aqui deve haver um tratamento de erro/retry
                e.printStackTrace()
            }
        }
    }
}