package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeCreditTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.CreditData
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.math.BigDecimal

class PaymentCreditCommandTest {

    private val auttarSDK = mockk<IAuttarSDK>(relaxed = true)
    private val valueTransaction = BigDecimal(85.0)

    @Test
    fun `when invoke and is success should return success result`() {
        val command = PaymentCreditCommand( valueTransaction)

        command.invoke(auttarSDK)

        val expected =  CreditData(
            valueTransaction,
            TypeCreditTransaction.CREDITO_GENERICO
        )
        verify { auttarSDK.paymentTransaction(expected) }
    }

}