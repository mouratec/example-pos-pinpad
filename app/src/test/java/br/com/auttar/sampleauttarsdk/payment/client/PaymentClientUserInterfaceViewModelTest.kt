package br.com.auttar.sampleauttarsdk.payment.client

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.auttar.sdk.interfaces.ClientUserInterface
import br.com.auttar.sdk.interfaces.IAuttarSDK
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class PaymentClientUserInterfaceViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val auttarSDK = mockk<IAuttarSDK>(relaxed = true)

    private lateinit var paymentViewModel: PaymentClientUserInterfaceViewModel
    private lateinit var clientUserInterface: ClientUserInterface

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { auttarSDK.connectUI(any()) } answers {
            clientUserInterface = firstArg()
        }
        paymentViewModel = PaymentClientUserInterfaceViewModel(auttarSDK)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when receive showQrCode should set status OnShowQrCode`() {
        val qrCodeImage = mockk<Bitmap>()
        val pinCode = "pinCode"
        val transactionValue = "transactionValue"
        val transactionType = "transactionType"

        clientUserInterface.showQrCode(qrCodeImage, pinCode, transactionValue, transactionType)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnShowQrCode(
                qrCodeImage,
                pinCode,
                transactionValue,
                transactionType
            )
        )
    }

    @Test
    fun `when receive hideQrCode should set status OnHideQrCode`() {
        clientUserInterface.hideQrCode()

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnHideQrCode
        )
    }

    @Test
    fun `when receive askGenericCapture should set status OnAskGenericCapture`() {
        val text = "TEXT"
        val mask = "MASK"

        clientUserInterface.askGenericCapture(text, mask, 0, 0)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnAskGenericCapture(
                text,
                mask
            )
        )
    }

    @Test
    fun `when receive askForValue should set status OnAskForValue`() {
        val text = "TEXT"

        clientUserInterface.askForValue(text)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnAskForValue(
                text
            )
        )
    }

    @Test
    fun `when receive messageDisplay should set status OnMessageDisplay`() {
        val text = "TEXT"

        clientUserInterface.messageDisplay(text)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnMessageDisplay(
                text
            )
        )
    }

    @Test
    fun `when receive askForDate should set status OnAskForDate`() {
        val text = "TEXT"
        val hint = "HINT"

        clientUserInterface.askForDate(text, hint)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnAskForDate(
                text,
                hint
            )
        )
    }

    @Test
    fun `when receive cleanDisplay should set status OnCleanDisplay`() {
        clientUserInterface.cleanDisplay()

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnCleanDisplay
        )
    }

    @Test
    fun `when receive askForConfirm should set status OnAskForConfirm`() {
        clientUserInterface.askForConfirm("")

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnAskForConfirm
        )
    }

    @Test
    fun `when receive askSelect should set status OnAskSelect`() {
        val text = "TEXT"
        val options = listOf("OPTION 1", "OPTION 2")

        clientUserInterface.askSelect(text, options.toTypedArray())

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnAskSelect(
                text,
                options
            )
        )
    }

    @Test
    fun `when receive showInsertCardMessage should set status OnShowInsertCardMessage`() {
        val text = "TEXT"

        clientUserInterface.showInsertCardMessage(text)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnShowInsertCardMessage(
                text
            )
        )
    }

    @Test
    fun `when receive showPin should set status OnShowPin`() {
        val text = "TEXT"

        clientUserInterface.showPin(text)

        assert(
            paymentViewModel.clientUserInterfaceState.value == PaymentClientUserInterfaceState.OnShowPin(
                text
            )
        )
    }
}