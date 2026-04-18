package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.common.transaction.model.type.TypeInitializeTransaction
import br.com.auttar.sdk.interfaces.IAuttarSDK
import br.com.auttar.sdk.transaction.model.data.InitializeData

class PaymentInitializationCommand : PaymentCommand {
    override fun invoke(auttarSDK: IAuttarSDK) {
        // A transação 225 é disparada através do InitializeData ou via código de operação administrativa
        // No SDK Auttar, transações de inicialização utilizam InitializeData
        auttarSDK.paymentTransaction(InitializeData(TypeInitializeTransaction.CARGA_TABELAS_FORCADA))
    }
}
