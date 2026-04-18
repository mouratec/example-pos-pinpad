package br.com.auttar.sampleauttarsdk.models

import br.com.auttar.sdk.interfaces.transaction.model.IAuttarPhone
import br.com.auttar.sdk.interfaces.transaction.model.IMerchantMultiEC
import br.com.auttar.sdk.interfaces.transaction.model.TefResultInterface
import br.com.auttar.sdk.transaction.model.data.AuttarPhone

data class FakeTefResult(
    override val returnCode: String = "",
    override val codeError: String = ""
) : TefResultInterface {
    override var sequenceDaySdk: Int = 0
    override var dateSdk: String = ""
    override var pixReceiverPSP: String = ""
    override var pixTransactionId: String = ""
    override var nameAcquired: String = ""
    override var flag: String = ""
    override var dateTime: String = ""
    override var installments: Int = 0
    override var identifierMultiEC: String = ""
    override var transactionCodeCTF: String = ""
    override var cardNumber: String = ""
    override var authorizedNSU: String = ""
    override var approvedCode: String = ""
    override var terminal: String = ""
    override var transacionCode: Int = 0
    override var splitPaymentMarketPlace: String = ""
    override var customerPaymentReceipt: String = ""
    override var shopkeeperPaymentReceipt: String = ""
    override var reducedPaymentReceipt: String = ""
    override var nsuCTF: Int = 0
    override var splitPaymentID: String = ""
    override var authorizedCode: String = ""
    override var display: String = ""
    override var dynamicCoupon: ArrayList<Pair<String, String>> = arrayListOf()
    override var messageAction: String = ""
    override var sequenceTransaction: Int = 0
    override var phone: IAuttarPhone = AuttarPhone()
    override var auttarMerchantMultiEC: ArrayList<IMerchantMultiEC>? = null
    override var multiTransactionCode: String? = null
    override var vanCode: Int = 0
    override var amount: String = ""
    override var cashWithdraw: String = ""
    override var transactionHour: String = ""
    override var nsuCTFOriginal: Int = 0
    override var transactionAmount: String = ""
    override var autoConfirm: Boolean = false
    override var msgCTF: ArrayList<String> = arrayListOf()
    override var codRespAut: String = ""
    override var codSupport: String = codeError
    override var complementaryDataTef: String = ""
}


