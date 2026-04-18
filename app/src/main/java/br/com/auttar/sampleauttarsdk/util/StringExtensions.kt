package br.com.auttar.sampleauttarsdk.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toBrazilianCurrency(): String {
    return try {
        val value = BigDecimal(this)
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        format.format(value)
    } catch (e: Exception) {
        "Valor inválido"
    }
}

fun String.formatDateSdkToDayMonthYear(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parser = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = formatter.parse(this)

    return parser.format(date)
}

fun String.Companion.empty() = ""