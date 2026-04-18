package br.com.auttar.sampleauttarsdk.util

import android.text.Editable
import android.text.TextWatcher
import java.text.DecimalFormat

class CurrencyTextWatcher : TextWatcher {

    companion object {
        private const val CurrencyMask = "#,###,###,##0.00"
    }

    private var isEditing = false
    private var formatted: String = ""
    private var digits: String = ""

    override fun afterTextChanged(s: Editable) {
        if (!isEditing) {
            isEditing = true
            try {
                processMask(s)
            } finally {
                isEditing = false
            }
        }
    }

    private fun unmask(text: String): String {
        return text.replace("[^0-9]*".toRegex(), "")
    }

    private fun processMask(s: Editable) {
        try {
            digits = unmask(s.toString())
            if (digits.length > 12) {
                digits = digits.substring(0, 12)
            } else {
                val df = Brazil.currencyFormat as DecimalFormat
                df.applyPattern(CurrencyMask)
                formatted = df.format(digits.toDouble() / 100).toString()
                s.replace(0, s.length, formatted, 0, formatted.length);
            }
        } catch (nfe: NumberFormatException) {
            s.clear()
        }
        isEditing = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}