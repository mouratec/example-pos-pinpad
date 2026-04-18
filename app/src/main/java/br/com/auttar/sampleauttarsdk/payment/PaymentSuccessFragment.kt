package br.com.auttar.sampleauttarsdk.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.PaymentNavigationDirections
import br.com.auttar.sampleauttarsdk.R
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentSuccessBinding
import br.com.auttar.sampleauttarsdk.util.formatDateSdkToDayMonthYear
import br.com.auttar.sampleauttarsdk.util.setTextOrHide
import br.com.auttar.sampleauttarsdk.util.toBrazilianCurrency

class PaymentSuccessFragment : BaseBindingFragment<FragmentPaymentSuccessBinding>() {

    private val args: PaymentSuccessFragmentArgs by navArgs()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentSuccessBinding {
        blockOnBackPressed()
        return FragmentPaymentSuccessBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setBindings()
    }

    private fun setListeners() {
        binding.btnOk.setOnClickListener {
            navToAction(PaymentNavigationDirections.actionToPaymentOptions())
        }
    }

    private fun setBindings() = with(binding) {
        val paymentData = args.paymentSuccessData
        val amountFormatted = paymentData.amount.toBrazilianCurrency()

        tvDisplay.setTextOrHide(paymentData.display)
        tvNsu.setTextOrHide(value = paymentData.nsuCTF.toString(), formatResId = R.string.nsu_ctf)
        tvAuthorizedNsu.setTextOrHide(value = paymentData.authorizedNSU, R.string.authorized_nsu)
        tvAmount.setTextOrHide(value = amountFormatted, formatResId = R.string.amount)
        tvTransactionId.setTextOrHide(value = paymentData.transactionId, formatResId = R.string.transaction_id)
        tvReceiverPsp.setTextOrHide(value = paymentData.receiverPSP, formatResId = R.string.receiver_psp)
        tvStatusPixQuery.setTextOrHide(
            value = paymentData.messageAction,
            formatResId = R.string.message_action_status
        )

        if (paymentData.dateSdk.isNotEmpty()) {
            val dateFormatted = paymentData.dateSdk.formatDateSdkToDayMonthYear()
            tvDateSdk.text = getString(R.string.dateSdk, dateFormatted)
        }
    }

}