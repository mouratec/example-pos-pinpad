package br.com.auttar.sampleauttarsdk.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object CurrencyFormatter {

    fun format(input: String): String {
        val digits = input.filter { it.isDigit() }

        if (digits.isEmpty()) return ""

        val bigDecimal = BigDecimal(digits).setScale(2).divide(BigDecimal(100))
        val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
        val formatter = DecimalFormat("#,##0.00", symbols)

        return formatter.format(bigDecimal)
    }

    fun toBigDecimal(formatted: String): BigDecimal {
        val clean = formatted.replace(Regex("\\D"),"")
        return if (clean.isEmpty()) BigDecimal.ZERO else BigDecimal(clean).setScale(2).divide(BigDecimal(100))
    }
}