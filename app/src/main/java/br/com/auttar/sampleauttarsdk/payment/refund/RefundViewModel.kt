package br.com.auttar.sampleauttarsdk.payment.refund

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class RefundViewModel: ViewModel() {

    private val _amount = MutableLiveData<BigDecimal>()
    val amount: LiveData<BigDecimal> = _amount

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun validateAmount(amount: BigDecimal) {
        if (amount <= BigDecimal.ZERO) {
            _errorMessage.value = "Valor inválido"
            return
        }
        _amount.value = amount
    }
}