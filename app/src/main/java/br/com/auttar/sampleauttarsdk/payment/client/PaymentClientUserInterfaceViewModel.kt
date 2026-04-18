package br.com.auttar.sampleauttarsdk.payment.client

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.auttar.sdk.common.data.ui.RefundConfirmData
import br.com.auttar.sdk.interfaces.ClientUserInterface
import br.com.auttar.sdk.interfaces.IAuttarSDK

class PaymentClientUserInterfaceViewModel(
    auttarSdk: IAuttarSDK
) : ViewModel() {

    private val _clientUserInterfaceState = MutableLiveData<PaymentClientUserInterfaceState>()
    val clientUserInterfaceState: LiveData<PaymentClientUserInterfaceState> =
        _clientUserInterfaceState

    private val _clientUserInterface = object : ClientUserInterface {

        override fun showQrCode(
            qrCodeImage: Bitmap, pinCode: String, transactionValue: String, transactionType: String
        ) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnShowQrCode(
                    qrCodeImage, pinCode, transactionValue, transactionType
                )
            )
        }

        override fun hideQrCode() {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.OnHideQrCode)
        }

        override fun askGenericCapture(
            display: String, mask: String, maxLength: Int, editionMode: Int
        ) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnAskGenericCapture(
                    display, mask
                )
            )
        }

        override fun askForValue(display: String) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnAskForValue(
                    display
                )
            )
        }

        override fun messageDisplay(message: String) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnMessageDisplay(
                    message
                )
            )
        }

        override fun showContactlessCard(status: String) {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.ShowContactless(status))
        }

        override fun askForDate(display: String, mask: String) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnAskForDate(
                    display, mask
                )
            )
        }

        override fun cleanDisplay() {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.OnCleanDisplay)
        }

        override fun askForConfirm(display: String) {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.OnAskForConfirm)
        }

        override fun askForConfirmRefund(refundData: RefundConfirmData) {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.OnAskForConfirm)
        }

        override fun askSelect(display: String, options: Array<String>) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnAskSelect(
                    display, options.toList()
                )
            )
        }

        override fun showInsertCardMessage(display: String) {
            _clientUserInterfaceState.postValue(
                PaymentClientUserInterfaceState.OnShowInsertCardMessage(
                    display
                )
            )
        }

        override fun showPin(display: String) {
            _clientUserInterfaceState.postValue(PaymentClientUserInterfaceState.OnShowPin(display))
        }
    }

    init {
        auttarSdk.connectUI(_clientUserInterface)
    }
}