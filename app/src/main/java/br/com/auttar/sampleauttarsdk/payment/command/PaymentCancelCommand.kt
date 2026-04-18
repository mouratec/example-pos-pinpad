package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.CancellationData
import java.math.BigDecimal

data class PaymentCancelCommand(val amount: BigDecimal): PaymentCommand {
    override fun invoke(auttarSDK: IAuttarSDK) {
        auttarSDK.paymentTransaction(
            data = CancellationData(
                amount = amount
            )
        )
    }
}