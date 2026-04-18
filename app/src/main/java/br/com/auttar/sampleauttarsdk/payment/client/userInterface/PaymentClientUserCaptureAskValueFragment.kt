package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientCaptureAskValueOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientCancelOperationCommand
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientSetValueCommand
import br.com.auttar.sampleauttarsdk.util.Brazil
import br.com.auttar.sampleauttarsdk.util.CurrencyTextWatcher
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.NumberFormat


class PaymentClientUserCaptureAskValueFragment :
    BaseBindingFragment<FragmentPaymentClientCaptureAskValueOperationBinding>() {

    private val args by navArgs<PaymentClientUserCaptureAskValueFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientCaptureAskValueOperationBinding {
        return FragmentPaymentClientCaptureAskValueOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEditText()
        binding.textViewInfo.text = args.text
        showKeyboard(binding.editTextValue)
    }

    private fun bindEditText() = with(binding) {
        val formatted: String = NumberFormat.getCurrencyInstance().format(0)
        editTextValue.addTextChangedListener(CurrencyTextWatcher())
        editTextValue.textAlignment = EditText.TEXT_ALIGNMENT_CENTER
        editTextValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        editTextValue.keyListener = DigitsKeyListener.getInstance("0123456789.,")
        editTextValue.textLocale = Brazil.locale
        editTextValue.setText(formatted)
        editTextValue.requestFocus()
        editTextValue.post {
            showKeyboard(editTextValue)
        }

        editTextValue.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        paymentViewModel.executeClientCommand(ClientSetValueCommand(binding.editTextValue.text.toString()))
                        return@OnKeyListener true
                    }

                    KeyEvent.KEYCODE_BACK -> {
                        paymentViewModel.executeClientCommand(ClientCancelOperationCommand)
                        return@OnKeyListener true
                    }
                }
            }
            false
        })
    }
}