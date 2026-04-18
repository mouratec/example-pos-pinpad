package br.com.auttar.sampleauttarsdk.payment.client.command

import br.com.auttar.sdk.interfaces.ResponseUserInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ClientCancelDateCommandTest {

    private val command = ClientCancelDateCommand
    private val responseUserInterface = mockk<ResponseUserInterface>(relaxed = true)

    @Test
    fun `when invoke should invoke askDate`() {
        command.invoke(responseUserInterface)

        verify { responseUserInterface.askDate("", true) }
    }
}