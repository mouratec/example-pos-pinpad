package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeDebitTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.DebitData
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.math.BigDecimal

class PaymentDebitCommandTest {

    private val auttarSDK = mockk<IAuttarSDK>(relaxed = true)
    private val valueTransaction = BigDecimal(85.0)

    @Test
    fun `when invoke and is success should return success result`() {
        val command = PaymentDebitCommand( valueTransaction)

        command.invoke(auttarSDK)

        val expected =  DebitData(
            valueTransaction,
            TypeDebitTransaction.DEBITO_GENERICO
        )
        verify { auttarSDK.paymentTransaction(expected) }
    }
}