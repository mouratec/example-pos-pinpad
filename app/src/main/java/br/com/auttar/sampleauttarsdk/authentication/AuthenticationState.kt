package br.com.auttar.sampleauttarsdk.authentication

sealed class AuthenticationState {
    data object Unlogged : AuthenticationState()
    data object OnLogged : AuthenticationState()
    data object OnLoading : AuthenticationState()
    data class OnErrorLogin(val error: String) : AuthenticationState()
}
