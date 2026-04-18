package br.com.auttar.sampleauttarsdk

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import br.com.auttar.sampleauttarsdk.authentication.AuthenticationState
import br.com.auttar.sampleauttarsdk.authentication.AuthenticationViewModel
import br.com.auttar.sampleauttarsdk.databinding.ActivityMainBinding
import br.com.auttar.sampleauttarsdk.util.safeNavigate
import br.com.auttar.sdk.Auttar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Auttar.getConfigSDK().scanPermissions(this)
        observeLoginState()
    }

    private fun observeLoginState(){

        authenticationViewModel.loginState.observe(this) { state ->
            when (state) {
                is AuthenticationState.OnErrorLogin -> onErrorLogin(state.error)
                AuthenticationState.OnLoading -> showLoading()
                AuthenticationState.OnLogged -> onLogged()
                AuthenticationState.Unlogged -> unlogged()
            }
        }
    }

    private fun onErrorLogin(error: String) {
        hideLoading()
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onLogged() {
        hideLoading()
        navToAction(MainNavigationDirections.actionToPayment())
    }

    private fun unlogged() {
        hideLoading()
        navToAction(MainNavigationDirections.actionLogoutToAuthentication())
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun navToAction(directions: NavDirections){
        findNavController(R.id.nav_host_fragment_content_main).safeNavigate(directions)
    }
}