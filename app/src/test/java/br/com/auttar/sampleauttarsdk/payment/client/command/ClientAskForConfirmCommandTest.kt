package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientAskForConfirmCommandTest {

    private val command = ClientAskForConfirmCommand(true)
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askForConfirm`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askForConfirm(command.askForConfirm) }
    }
}