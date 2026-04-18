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
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.Locale

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 1. Configuração de Autenticação Global (Java Net)
        Authenticator.setDefault(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("hti", "h123456".toCharArray())
            }
        })

        // 2. Localização
        Locale.setDefault(Brazil.locale)

        // 3. Inicialização do Koin da Aplicação
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@SampleApplication)
            modules(modulesAuttarKoin(this@SampleApplication))
        }

        // 4. Inicialização do SDK (Sincronamente para evitar NoDefinitionFoundException)
        Auttar.initHomolog(application = this)

        // 5. Configurações Assíncronas (Métodos Suspend)
        MainScope().launch {
            try {
                val config = Auttar.getConfigSDK()
                config.userConfiguration.setWorkDir(this@SampleApplication.filesDir.absolutePath)
                config.userConfiguration.setAllowsPinpad(true)
                config.userConfiguration.setPinpadUSB(false)
                config.userConfiguration.setIsPinPadBluetooth(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}