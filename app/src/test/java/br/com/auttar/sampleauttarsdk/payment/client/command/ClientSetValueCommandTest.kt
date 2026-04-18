package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientSetValueCommandTest {

    private val command = ClientSetValueCommand("R$ 2.500,57")
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askDate`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askForValue("250057", false) }
    }
}