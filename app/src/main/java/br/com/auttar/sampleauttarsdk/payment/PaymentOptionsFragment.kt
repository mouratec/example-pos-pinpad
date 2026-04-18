package br.com.auttar.sampleauttarsdk.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.auttar.sampleauttarsdk.R
import br.com.auttar.sampleauttarsdk.authentication.AuthenticationViewModel
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientSelectOptionOperationBinding
import br.com.auttar.sampleauttarsdk.payment.command.PaymentCreditCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentCreditInstallmentCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentCreditTypedCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentDebitCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentInitializationCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentPixCommand
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.math.BigDecimal

class PaymentOptionsFragment :
    BaseBindingFragment<FragmentPaymentClientSelectOptionOperationBinding>() {

    private val paymentViewModel: PaymentViewModel by activityViewModel()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPaymentClientSelectOptionOperationBinding {
        return FragmentPaymentClientSelectOptionOperationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentViewModel.runInitialization()
        setListeners()
        setObservers()
    }

    private fun setListeners() = with(binding) {
        btnCredit.setOnClickListener {
            paymentViewModel.executePaymentCommand(
                PaymentCreditCommand(BigDecimal(INITIAL_VALUE_TRANSACTION))
            )
        }

        btnCreditInstallments.setOnClickListener {
            paymentViewModel.executePaymentCommand(
                PaymentCreditInstallmentCommand(BigDecimal(INITIAL_VALUE_TRANSACTION))
            )
        }

        btnCreditTyped.setOnClickListener {
            paymentViewModel.executePaymentCommand(
                PaymentCreditTypedCommand(BigDecimal(INITIAL_VALUE_TRANSACTION))
            )
        }

        btnDebit.setOnClickListener {
            paymentViewModel.executePaymentCommand(
                PaymentDebitCommand(BigDecimal(INITIAL_VALUE_TRANSACTION))
            )
        }

        btnPix.setOnClickListener {
            paymentViewModel.executePaymentCommand(
                PaymentPixCommand(BigDecimal(INITIAL_VALUE_TRANSACTION))
            )
        }

        btnRefund.setOnClickListener {
            navToAction(PaymentOptionsFragmentDirections.actionToRefund())
        }

        btnInitialize.setOnClickListener {
            paymentViewModel.executePaymentCommand(PaymentInitializationCommand())
        }

        btnSaveLogs.setOnClickListener {
            val path = requireContext().getExternalFilesDir(null)?.absolutePath ?: ""
            paymentViewModel.saveLogs(path)
        }

        btnConfigPinpad.setOnClickListener {
            navToAction(PaymentOptionsFragmentDirections.actionToPinpadConfig())
        }

        btnLogout.setOnClickListener {
            authenticationViewModel.logout()
        }
    }

    private fun setObservers() = with(binding) {
        authenticationViewModel.terminal.observe(viewLifecycleOwner) { terminal ->
            tvTerminal.text = getString(R.string.terminal, terminal)
        }
    }

    companion object {
        private const val INITIAL_VALUE_TRANSACTION = 0
    }
}