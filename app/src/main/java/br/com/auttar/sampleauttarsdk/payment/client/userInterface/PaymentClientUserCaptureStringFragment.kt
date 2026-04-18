package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientCaptureStringOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientAskForGenericCommand
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientCancelOperationCommand
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentClientUserCaptureStringFragment :
    BaseBindingFragment<FragmentPaymentClientCaptureStringOperationBinding>() {

    private val args by navArgs<PaymentClientUserCaptureStringFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientCaptureStringOperationBinding {
        return FragmentPaymentClientCaptureStringOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.title.text = args.text
        setMask()

        showKeyboard(binding.inputLayout.input)
        binding.inputLayout.input.setOnClickListener {
            showKeyboard(binding.inputLayout.input)
        }

        binding.inputLayout.input.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard()
                    paymentViewModel.executeClientCommand(ClientAskForGenericCommand(binding.inputLayout.input.text.toString()))
                    return@OnKeyListener true
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    paymentViewModel.executeClientCommand(ClientCancelOperationCommand)
                    return@OnKeyListener true
                }
            }
            false
        })

        binding.inputLayout.btnCancel.setOnClickListener {
            paymentViewModel.executeClientCommand(ClientCancelOperationCommand)
        }

        binding.inputLayout.btnContinue.setOnClickListener {
            hideSoftKeyboard()
            paymentViewModel.executeClientCommand(ClientAskForGenericCommand(binding.inputLayout.input.text.toString()))
        }
    }

    private fun setMask() {
        with(args.mask) {
            if (isNotEmpty()) {
                binding.inputLayout.input.filters =
                    arrayOf<InputFilter>(InputFilter.LengthFilter(length))
            }

            binding.inputLayout.input.textAlignment = EditText.TEXT_ALIGNMENT_TEXT_END
            if (contains('@') || isEmpty()) {
                binding.inputLayout.input.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                binding.inputLayout.input.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }
}