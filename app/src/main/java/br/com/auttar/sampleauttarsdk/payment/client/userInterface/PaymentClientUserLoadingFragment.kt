package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientLoadingOperationBinding

class PaymentClientUserLoadingFragment :
    BaseBindingFragment<FragmentPaymentClientLoadingOperationBinding>() {

    private val args: PaymentClientUserLoadingFragmentArgs by navArgs()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientLoadingOperationBinding {
        return FragmentPaymentClientLoadingOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loading.description.text = args.text
    }
}