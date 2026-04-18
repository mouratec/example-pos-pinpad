package br.com.auttar.sampleauttarsdk.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

object Brazil {
    val locale = Locale("pt", "BR")
    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    fun round(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}