package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

data class ClientAskSelectCommand(val option: String) : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        responseUserInterface.askSelect(option, false)
    }
}
