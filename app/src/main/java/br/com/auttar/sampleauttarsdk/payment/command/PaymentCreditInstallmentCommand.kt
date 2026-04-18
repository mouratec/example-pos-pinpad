package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeCreditTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.CreditData
import java.math.BigDecimal

data class PaymentCreditInstallmentCommand(
    val amount: BigDecimal, 
    val isIssuerInstallment: Boolean = false
) : PaymentCommand {

    override fun invoke(auttarSDK: IAuttarSDK) {
        val type = if (isIssuerInstallment) {
            TypeCreditTransaction.CREDITO_PARCELADO_COM_JUROS
        } else {
            TypeCreditTransaction.CREDITO_PARCELADO_SEM_JUROS
        }
        
        auttarSDK.paymentTransaction(
            CreditData(
                amount,
                type
            )
        )
    }
}