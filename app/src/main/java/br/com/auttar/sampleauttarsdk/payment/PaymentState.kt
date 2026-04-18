package br.com.auttar.sampleauttarsdk.payment

import br.com.auttar.sampleauttarsdk.payment.command.PaymentCommand
import br.com.auttar.sdk.interfaces.transaction.model.TefResultInterface

sealed class PaymentState {
    data class OnSuccess(val command: PaymentCommand?, val data: TefResultInterface) : PaymentState()
    data class OnError(val command: PaymentCommand?, val data: TefResultInterface) : PaymentState()
    data class OnMessage(val message: String) : PaymentState()
    data object OnLoading: PaymentState()
    data object OnIdle: PaymentState()
}
