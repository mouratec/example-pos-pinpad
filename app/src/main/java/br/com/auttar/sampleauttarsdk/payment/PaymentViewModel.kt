package br.com.auttar.sampleauttarsdk.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentCommand
import br.com.auttar.sampleauttarsdk.payment.command.PaymentInitializationCommand
import br.com.auttar.sdk.Auttar
import br.com.auttar.sdk.AuttarTransactionState
import br.com.auttar.sdk.interfaces.IAuttarSDK
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val auttarSdk: IAuttarSDK
) : ViewModel() {

    private val _paymentState = MutableLiveData<PaymentState>()
    val paymentState: LiveData<PaymentState> = _paymentState

    private var currentCommand: PaymentCommand? = null
    private var configSDK = Auttar.getConfigSDK()
    private var isInitialized = false

    val pinpadUsb: Boolean get() = configSDK.userConfiguration.pinpadUsb.value
    val pinpadBluetooth: Boolean get() = configSDK.userConfiguration.isPinPadBluetooth.value
    val allowsPinpad: Boolean get() = configSDK.userConfiguration.allowsPinpad.value
    val macAddress: String get() = configSDK.userConfiguration.macAddressPinpad.value

    init {
        collectTransactionStateFlow()
    }

    fun configurationData(isPinPadUsb: Boolean) {
        viewModelScope.launch {
            configSDK.userConfiguration.setPinpadUSB(isPinPadUsb)
        }
    }

    fun setPinpadCommunication(type: String, macAddress: String = "") {
        viewModelScope.launch {
            when (type) {
                "POS" -> {
                    // Para POS Interno, desativamos as flags de Pinpad Externo (USB/Bluetooth)
                    // Mas mantemos AllowsPinpad como true para que o SDK inicialize os drivers de teclado internos
                    configSDK.userConfiguration.setAllowsPinpad(true)
                    configSDK.userConfiguration.setPinpadUSB(false)
                    configSDK.userConfiguration.setIsPinPadBluetooth(false)
                }
                "USB" -> {
                    configSDK.userConfiguration.setAllowsPinpad(true)
                    configSDK.userConfiguration.setPinpadUSB(true)
                    configSDK.userConfiguration.setIsPinPadBluetooth(false)
                }
                "BLUETOOTH" -> {
                    configSDK.userConfiguration.setAllowsPinpad(true)
                    configSDK.userConfiguration.setPinpadUSB(false)
                    configSDK.userConfiguration.setIsPinPadBluetooth(true)
                    if (macAddress.isNotEmpty()) {
                        configSDK.userConfiguration.setMacAddressPinpad(macAddress)
                    }
                }
            }
            _paymentState.postValue(PaymentState.OnMessage("Configuração aplicada: $type. Reinicie a transação."))
        }
    }

    fun saveLogs(path: String) {
        _paymentState.value = PaymentState.OnLoading
        configSDK.saveSupportFiles(path) { resultPath ->
            _paymentState.postValue(PaymentState.OnMessage("Logs salvos em: $resultPath"))
        }
    }

    private fun collectTransactionStateFlow() {
        viewModelScope.launch {
            auttarSdk.transactionStateFlow.collect {
                _paymentState.value = it.toPaymentState()
            }
        }
    }

    private fun AuttarTransactionState.toPaymentState(): PaymentState {
        return when (this) {
            AuttarTransactionState.Idle -> PaymentState.OnIdle
            AuttarTransactionState.Processing -> PaymentState.OnLoading
            is AuttarTransactionState.Done -> {
                val isSuccess = this.typeTransaction.code == 0
                if (isSuccess) {
                    PaymentState.OnSuccess(
                        command = currentCommand,
                        data = this.result
                    )
                } else {
                    PaymentState.OnError(
                        command = currentCommand,
                        data = this.result
                    )
                }
            }
        }
    }

    fun runInitialization() {
        if (!isInitialized) {
            isInitialized = true
            executePaymentCommand(PaymentInitializationCommand())
        }
    }

    fun executePaymentCommand(command: PaymentCommand) {
        command.executeCommand()
    }

    fun executeClientCommand(command: ClientCommand) {
        command.executeCommand()
    }

    private fun PaymentCommand.executeCommand() {
        currentCommand = this
        invoke(auttarSDK = auttarSdk)
    }

    private fun ClientCommand.executeCommand() {
        invoke(auttarSdk.getObjectResponseCallback())
    }
}