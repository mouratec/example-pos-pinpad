package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface

sealed interface ClientCommand {
    fun invoke(responseUserInterface: ResponseUserInterface)
}