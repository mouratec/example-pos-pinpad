package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientCancelOperationCommandTest {

    private val command = ClientCancelOperationCommand
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askForGeneric`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askForGeneric("", true) }
    }
}