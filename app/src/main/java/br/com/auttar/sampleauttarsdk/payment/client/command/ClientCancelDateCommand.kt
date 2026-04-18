package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

object ClientCancelDateCommand : ClientCommand {

    override fun invoke(responseUserInterface: ResponseUserInterface) {
        responseUserInterface.askDate("", true)
    }
}