package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeCreditTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.CreditData
import java.math.BigDecimal

data class PaymentCreditTypedCommand(val amount: BigDecimal) : PaymentCommand {

    override fun invoke(auttarSDK: IAuttarSDK) {
        auttarSDK.paymentTransaction(
            CreditData(
                amount,
                TypeCreditTransaction.CREDITO_DIGITADO
            )
        )
    }
}