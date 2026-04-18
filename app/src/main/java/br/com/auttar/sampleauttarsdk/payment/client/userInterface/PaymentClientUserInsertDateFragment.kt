package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientInsertDateOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientCancelDateCommand
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientDateSelectedCommand
import br.com.auttar.sampleauttarsdk.util.askDateOnClickListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentClientUserInsertDateFragment :
    BaseBindingFragment<FragmentPaymentClientInsertDateOperationBinding>() {

    private val args by navArgs<PaymentClientUserInsertDateFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientInsertDateOperationBinding {
        return FragmentPaymentClientInsertDateOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.input.hint = args.hint
        binding.inputLayout.input.askDateOnClickListener()
        binding.inputLayout.title.text = args.text

        binding.inputLayout.btnContinue.setOnClickListener {
            val date = binding.inputLayout.input.text.toString()
            paymentViewModel.executeClientCommand(ClientDateSelectedCommand(date))
        }

        binding.inputLayout.btnCancel.setOnClickListener {
            paymentViewModel.executeClientCommand(ClientCancelDateCommand)
        }
    }
}