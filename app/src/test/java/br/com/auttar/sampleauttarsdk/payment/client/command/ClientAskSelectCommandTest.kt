package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientAskSelectCommandTest {

    private val command = ClientAskSelectCommand("OPTION")
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askSelect`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askSelect(command.option, false) }
    }
}