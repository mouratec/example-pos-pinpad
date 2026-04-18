package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientAskForGenericCommandTest {

    private val command = ClientAskForGenericCommand("ITEM")
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askForGeneric`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askForGeneric(command.item, false) }
    }
}