package br.com.auttar.sampleauttarsdk.util

import android.app.DatePickerDialog
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import java.util.Calendar
import java.util.Locale

fun EditText.askDateOnClickListener() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        focusable = View.NOT_FOCUSABLE
    }else{
        clearFocus()
    }
    setOnClickListener {
        askDate { day, month, year ->
            val sDate = "$day/$month/$year"
            setText(sDate)
        }
    }
}

fun EditText.addCurrencyMask() {
    var current = ""

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val newText = p0.toString()
            if (newText == current) return

            val formatted = CurrencyFormatter.format(newText)
            current = formatted

            removeTextChangedListener(this)
            setText(formatted)
            setSelection(formatted.length)
            addTextChangedListener(this)
        }
    })
}

private fun EditText.askDate(onDateSelected: (String, String, String) -> Unit) {
    try {
        val picker: DatePickerDialog
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr[Calendar.DAY_OF_MONTH]
        val month: Int = cldr[Calendar.MONTH]
        val year: Int = cldr[Calendar.YEAR]

        picker = DatePickerDialog(
            this.context,
            { _, yearNow, monthOfYear, dayOfMonth ->
                val dayLocal = String.format(Locale("pt", "BR"), "%02d", dayOfMonth)
                val monthLocal = String.format(Locale("pt", "BR"), "%02d", monthOfYear + 1)
                val yearLocal = yearNow.toString().substring(2)
                onDateSelected(dayLocal, monthLocal, yearLocal)
            }, year, month, day
        )
        picker.show()

    } catch (e: Exception) {
        e.printStackTrace()
    }
}