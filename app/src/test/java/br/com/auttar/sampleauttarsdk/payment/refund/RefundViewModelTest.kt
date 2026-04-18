package br.com.auttar.sampleauttarsdk.payment.refund

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.auttar.sampleauttarsdk.util.CurrencyFormatter
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal
import kotlin.test.assertEquals

class RefundViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RefundViewModel

    private val amountObserver = mockk<Observer<BigDecimal>>(relaxed = true)
    private val errorObserver = mockk<Observer<String>>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = RefundViewModel()
        viewModel.amount.observeForever(amountObserver)
        viewModel.errorMessage.observeForever(errorObserver)
    }

    @After
    fun tearDown() {
        viewModel.amount.removeObserver(amountObserver)
        viewModel.errorMessage.removeObserver(errorObserver)
    }

    @Test
    fun `should update value correctly with formatted input`() {
        val inputFormatted = "1.202,21"
        val valueConverted = CurrencyFormatter.toBigDecimal(inputFormatted)

        viewModel.validateAmount(valueConverted)

        assertEquals(BigDecimal("1202.21"), viewModel.amount.value)
    }

    @Test
    fun `should emit error when amount is zero`() {
        viewModel.validateAmount(BigDecimal.ZERO)

        verify { errorObserver.onChanged(INVALID_VALUE_TXT) }
    }

    @Test
    fun `should emit error when amount is negative`() {
        viewModel.validateAmount(BigDecimal("-12.99"))

        verify { errorObserver.onChanged(INVALID_VALUE_TXT) }
    }

    @Test
    fun `should emit amount when value is positive`() {
        val validAmount = BigDecimal("202.21")

        viewModel.validateAmount(validAmount)

        verify { amountObserver.onChanged(validAmount) }
    }

    companion object {
        private const val INVALID_VALUE_TXT = "Valor inválido"
    }
}