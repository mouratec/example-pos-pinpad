package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.app.Instrumentation
import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientCaptureValueOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientCancelOperationCommand
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientSetValueCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.NumberFormat
import java.util.Locale

class PaymentClientUserCaptureValueFragment :
    BaseBindingFragment<FragmentPaymentClientCaptureValueOperationBinding>() {

    private val args by navArgs<PaymentClientUserCaptureValueFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientCaptureValueOperationBinding {
        return FragmentPaymentClientCaptureValueOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEditText()
        bindKeyboard()

        binding.textViewInfo.text = args.text
    }

    private fun bindKeyboard() {
        binding.keyboard.button0.setOnClickListener { sendKey(KeyEvent.KEYCODE_0) }
        binding.keyboard.button1.setOnClickListener { sendKey(KeyEvent.KEYCODE_1) }
        binding.keyboard.button2.setOnClickListener { sendKey(KeyEvent.KEYCODE_2) }
        binding.keyboard.button3.setOnClickListener { sendKey(KeyEvent.KEYCODE_3) }
        binding.keyboard.button4.setOnClickListener { sendKey(KeyEvent.KEYCODE_4) }
        binding.keyboard.button5.setOnClickListener { sendKey(KeyEvent.KEYCODE_5) }
        binding.keyboard.button6.setOnClickListener { sendKey(KeyEvent.KEYCODE_6) }
        binding.keyboard.button7.setOnClickListener { sendKey(KeyEvent.KEYCODE_7) }
        binding.keyboard.button8.setOnClickListener { sendKey(KeyEvent.KEYCODE_8) }
        binding.keyboard.button9.setOnClickListener { sendKey(KeyEvent.KEYCODE_9) }

        binding.keyboard.buttonBack.setOnClickListener {
            sendKey(KeyEvent.KEYCODE_DEL)
        }
        binding.keyboard.buttonClose.setOnClickListener {
            sendKey(KeyEvent.KEYCODE_BACK)
        }
        binding.keyboard.buttonOk.setOnClickListener {
            sendKey(KeyEvent.KEYCODE_ENTER)
        }
    }

    private fun sendKey(keycode: Int) {
        val inst = Instrumentation()
        CoroutineScope(Dispatchers.IO).launch {
            inst.sendKeyDownUpSync(keycode)
        }
    }

    private fun bindEditText() {
        val formatted: String = NumberFormat.getCurrencyInstance().format(0)

        with(binding.editTextValue) {
            textAlignment = EditText.TEXT_ALIGNMENT_VIEW_START
            keyListener = DigitsKeyListener.getInstance("R$ 0123456789.,")
            textLocale = Locale("pt", "BR")
            setText(formatted)
            requestFocus()
            showSoftInputOnFocus = false
        }

        hideSoftKeyboard()

        binding.editTextValue.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        hideSoftKeyboard()
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