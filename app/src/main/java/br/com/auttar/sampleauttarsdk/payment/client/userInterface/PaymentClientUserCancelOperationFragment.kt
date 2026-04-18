package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientUserCancelOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientAskForConfirmCommand
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentClientUserCancelOperationFragment :
    BaseBindingFragment<FragmentPaymentClientUserCancelOperationBinding>() {

    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientUserCancelOperationBinding {
        blockOnBackPressed()
        return FragmentPaymentClientUserCancelOperationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnYes.setOnClickListener {
            paymentViewModel.executeClientCommand(ClientAskForConfirmCommand(false))
            navController.popBackStack()
        }

        binding.btnNo.setOnClickListener {
            paymentViewModel.executeClientCommand(ClientAskForConfirmCommand(true))
            navController.popBackStack()
        }
    }
}