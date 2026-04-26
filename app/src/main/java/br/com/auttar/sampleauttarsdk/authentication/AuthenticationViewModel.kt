package br.com.auttar.sampleauttarsdk.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.auttar.sdk.authentication.ParameterLoginIntegrationMonoEC
import br.com.auttar.sdk.interfaces.IAuttarConfigSDK
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.interfaces.authentication.LoginResultInterface
import br.com.auttar.sdk.interfaces.authentication.LoginResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val auttarSdk: IAuttarSDK,
    private val auttarSdkConfig: IAuttarConfigSDK
) : ViewModel() {

    private val _loginState = MutableLiveData<AuthenticationState>()
    val loginState: LiveData<AuthenticationState> = _loginState

    private val _terminal = MutableLiveData<String>()
    val terminal: LiveData<String> = _terminal

    private val _requestLoginCallback = object : LoginResultListener {
        override fun onResult(result: LoginResultInterface) {
            viewModelScope.launch(Dispatchers.Main) {
                val isSuccess = result.code == 0
                if (isSuccess) {
                    _loginState.postValue(AuthenticationState.OnLogged)
                    setTerminal()
                }else{
                    _loginState.postValue(AuthenticationState.OnErrorLogin(error = result.message))
                }
            }
        }
    }

    init {
        observeLoginState()
    }

    private fun observeLoginState() {
        viewModelScope.launch {
            auttarSdkConfig.isLogged.collect { logged ->
                when (logged) {
                    true -> {
                        _loginState.postValue(AuthenticationState.OnLogged)
                        setTerminal()
                    }
                    false -> {
                        _loginState.postValue(AuthenticationState.Unlogged)
                    }
                    null -> {
                        // Ainda inicializando ou em estado indeterminado
                        _loginState.postValue(AuthenticationState.OnLoading)
                    }
                }
            }
        }
    }

    fun onMonoECAuthenticate(
        user: String, password: String, cnpj: String
    ) {
        viewModelScope.launch {
            // Aguarda o SDK estar pronto antes de autenticar
            br.com.auttar.sampleauttarsdk.SampleApplication.isSdkReady.collect { isReady ->
                if (isReady) {
                    val data = ParameterLoginIntegrationMonoEC(
                        user,
                        password,
                        cnpj,
                        null
                    )

                    _loginState.postValue(AuthenticationState.OnLoading)

                    auttarSdk.authenticationMonoEC(data, _requestLoginCallback)
                    return@collect
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.Main) {
            auttarSdk.logout()
            _loginState.postValue(AuthenticationState.Unlogged)
        }
    }

    private fun setTerminal() {
        val userConfiguration = auttarSdkConfig.userConfiguration
        val terminal = userConfiguration.terminal.value
        _terminal.value = terminal
    }
}