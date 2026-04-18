package br.com.auttar.sampleauttarsdk.payment.command

import br.com.auttar.sdk.interfaces.transaction.model.TefResultInterface

interface PaymentCommandListener {
    fun onResponse(isSuccess: Boolean, data: TefResultInterface)
}