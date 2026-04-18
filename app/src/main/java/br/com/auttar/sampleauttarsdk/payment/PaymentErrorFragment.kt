package br.com.auttar.sampleauttarsdk.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.PaymentNavigationDirections
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentErrorBinding

class PaymentErrorFragment : BaseBindingFragment<FragmentPaymentErrorBinding>() {

    private val args: PaymentErrorFragmentArgs by navArgs()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentErrorBinding {
        blockOnBackPressed()
        return FragmentPaymentErrorBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()

        binding.btnOk.setOnClickListener {
            navToAction(PaymentNavigationDirections.actionToPaymentOptions())
        }
    }

    private fun setViews() = with(binding) {
        args.let { data ->
            tvTitleError.text = data.errorData.display
            tvMessageAction.text = data.errorData.messageAction
            tvCodSupportError.text = data.errorData.codSupport
        }
    }
}