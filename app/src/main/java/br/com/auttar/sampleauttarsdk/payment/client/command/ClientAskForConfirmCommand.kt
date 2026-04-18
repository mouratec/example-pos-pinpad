package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

data class ClientAskForConfirmCommand(val askForConfirm: Boolean) : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        responseUserInterface.askForConfirm(askForConfirm)
    }
}
