package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

data class ClientSetValueCommand(val transactionValue: String) : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        if (transactionValue.isBlank()) {
            responseUserInterface.askForValue("0", false)
            return
        }
        val d = transactionValue
            .replace(".", "")
            .replace(",", "")
            .replace(" ", "")
            .replace("R", "")
            .replace("$", "")
        responseUserInterface.askForValue(d, false)
    }
}
