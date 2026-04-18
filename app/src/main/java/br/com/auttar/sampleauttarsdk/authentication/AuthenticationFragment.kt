package br.com.auttar.sampleauttarsdk.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentAuthenticationBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AuthenticationFragment : BaseBindingFragment<FragmentAuthenticationBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthenticationBinding {
        return FragmentAuthenticationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Valores padrão para teste
        binding.etUser.setText("hti")
        binding.etPassword.setText("h123456")
        binding.etCnpj.setText("41602316000130")

        binding.btnLogin.setOnClickListener {
            onAuthenticationUser()
        }
    }

    private fun onAuthenticationUser() {
        val user = binding.etUser.text.toString()
        val password = binding.etPassword.text.toString()
        val cnpj = binding.etCnpj.text.toString()

        authenticationViewModel.onMonoECAuthenticate(
            user = user,
            password = password,
            cnpj = cnpj
        )
    }
}