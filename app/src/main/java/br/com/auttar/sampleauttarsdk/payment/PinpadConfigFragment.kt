package br.com.auttar.sampleauttarsdk.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPinpadConfigBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PinpadConfigFragment : BaseBindingFragment<FragmentPinpadConfigBinding>() {

    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPinpadConfigBinding {
        return FragmentPinpadConfigBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCurrentConfig()
        setListeners()
    }

    private fun loadCurrentConfig() {
        with(binding) {
            when {
                paymentViewModel.pinpadBluetooth -> {
                    rbBluetooth.isChecked = true
                    tilMacAddress.visibility = View.VISIBLE
                    etMacAddress.setText(paymentViewModel.macAddress)
                }
                paymentViewModel.pinpadUsb -> {
                    rbUsb.isChecked = true
                }
                else -> {
                    rbPos.isChecked = true
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            rgPinpadType.setOnCheckedChangeListener { _, checkedId ->
                tilMacAddress.visibility = if (checkedId == rbBluetooth.id) View.VISIBLE else View.GONE
            }

            btnSaveConfig.setOnClickListener {
                saveAndApply()
            }
        }
    }

    private fun saveAndApply() {
        with(binding) {
            val type = when (rgPinpadType.checkedRadioButtonId) {
                rbPos.id -> "POS"
                rbUsb.id -> "USB"
                rbBluetooth.id -> "BLUETOOTH"
                else -> "POS"
            }

            val mac = etMacAddress.text.toString().trim()
            
            if (type == "BLUETOOTH" && mac.isEmpty()) {
                tilMacAddress.error = "Informe o endereço MAC"
                return
            }

            paymentViewModel.setPinpadCommunication(type, mac)
            navController.popBackStack()
        }
    }
}