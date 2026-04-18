package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

data class ClientDateSelectedCommand(val text: String) : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        responseUserInterface.askDate(text.replace("/", ""), false)
    }
}
