package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeDebitTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.DebitData
import java.math.BigDecimal

data class PaymentDebitCommand(val amount: BigDecimal) : PaymentCommand {

    override fun invoke(
        auttarSDK: IAuttarSDK
    ) {
        auttarSDK.paymentTransaction(
            data = DebitData(
                amount,
                TypeDebitTransaction.DEBITO_GENERICO
            )
        )
    }
}