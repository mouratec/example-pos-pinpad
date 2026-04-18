package br.com.auttar.sampleauttarsdk.payment.refund

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentRefundBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.command.PaymentCancelCommand
import br.com.auttar.sampleauttarsdk.util.CurrencyFormatter
import br.com.auttar.sampleauttarsdk.util.addCurrencyMask
import br.com.auttar.sampleauttarsdk.util.showToast
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RefundFragment : BaseBindingFragment<FragmentRefundBinding>() {

    private val viewModel: RefundViewModel by viewModel()
    private val paymentViewModel: PaymentViewModel by activityViewModel()
    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRefundBinding.inflate(
        inflater,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOnBackPressed()
        setUpViews()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpOnBackPressed() = requireActivity()
        .onBackPressedDispatcher
        .addCallback(viewLifecycleOwner) { findNavController().popBackStack() }

    private fun setUpViews() {
        binding.etValue.addCurrencyMask()
    }

    private fun setUpListeners() = with(binding) {
        btnConfirm.setOnClickListener {
            val value = CurrencyFormatter.toBigDecimal(etValue.text.toString())
            viewModel.validateAmount(
                amount = value
            )
        }
    }

    private fun setUpObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
        return viewModel.amount.observe(viewLifecycleOwner) { value ->
            paymentViewModel.executePaymentCommand(
                PaymentCancelCommand(
                    amount = value
                )
            )
        }
    }
}

