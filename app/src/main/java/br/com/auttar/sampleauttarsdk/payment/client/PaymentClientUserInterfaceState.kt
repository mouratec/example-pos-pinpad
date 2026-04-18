package br.com.auttar.sampleauttarsdk.payment.client

import android.graphics.Bitmap

sealed class PaymentClientUserInterfaceState {
    data class OnShowQrCode(
        val image: Bitmap,
        val pinCode: String,
        val transactionValue: String,
        val transactionType: String
    ) : PaymentClientUserInterfaceState()

    data object OnHideQrCode : PaymentClientUserInterfaceState()
    data class OnAskGenericCapture(val text: String, val mask: String) : PaymentClientUserInterfaceState()
    data class OnAskForValue(val text: String) : PaymentClientUserInterfaceState()
    data class OnMessageDisplay(val text: String) : PaymentClientUserInterfaceState()
    data class OnAskForDate(val text: String, val hint: String) : PaymentClientUserInterfaceState()
    data object OnCleanDisplay : PaymentClientUserInterfaceState()
    data object OnAskForConfirm : PaymentClientUserInterfaceState()
    data class OnAskSelect(val text: String, val options: List<String>) : PaymentClientUserInterfaceState()
    data class OnShowInsertCardMessage(val text: String) : PaymentClientUserInterfaceState()
    data class OnShowPin(val text: String) : PaymentClientUserInterfaceState()
    data class ShowContactless(val text: String) : PaymentClientUserInterfaceState()
}
