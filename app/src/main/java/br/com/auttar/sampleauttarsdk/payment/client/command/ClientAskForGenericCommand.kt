package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

data class ClientAskForGenericCommand(val item: String) : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        responseUserInterface.askForGeneric(item, false)
    }
}
