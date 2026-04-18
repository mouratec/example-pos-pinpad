package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.bc.transaction.data.PixData
import br.com.auttar.sdk.common.transaction.model.type.TypePixTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import java.math.BigDecimal

data class PaymentPixCommand(val amount: BigDecimal) : PaymentCommand {

    override fun invoke(auttarSDK: IAuttarSDK) {
        auttarSDK.paymentTransaction(
            PixData(
                amount,
                TypePixTransaction.PAGAMENTO_PIX
            )
        )
    }
}