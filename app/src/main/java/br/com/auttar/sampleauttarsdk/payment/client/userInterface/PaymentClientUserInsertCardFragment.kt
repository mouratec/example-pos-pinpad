package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientInsertCardOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentClientUserInsertCardFragment :
    BaseBindingFragment<FragmentPaymentClientInsertCardOperationBinding>() {

    private val args by navArgs<PaymentClientUserInsertCardFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientInsertCardOperationBinding {
        blockOnBackPressed()
        return FragmentPaymentClientInsertCardOperationBinding.inflate(
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