package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientInsertPassOperationBinding

class PaymentClientUserInsertPassFragment :
    BaseBindingFragment<FragmentPaymentClientInsertPassOperationBinding>() {

    private val args by navArgs<PaymentClientUserInsertPassFragmentArgs>()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientInsertPassOperationBinding {
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        blockOnBackPressed()
        return FragmentPaymentClientInsertPassOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.text = args.text
    }
}