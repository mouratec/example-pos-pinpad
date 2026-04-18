package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.interfaces.IAuttarSDK

sealed interface PaymentCommand {
    fun invoke(auttarSDK: IAuttarSDK)
}
