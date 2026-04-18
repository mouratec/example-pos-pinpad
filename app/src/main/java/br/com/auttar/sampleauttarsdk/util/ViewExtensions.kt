package br.com.auttar.sampleauttarsdk.util

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

fun TextView.setTextOrHide(value: String?, formatResId: Int? = null) {

    if (!value.isNullOrBlank()) {
        text = formatResId?.let { context.getString(it, value) } ?: value
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }

}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    if (message.isBlank()) return
    Toast.makeText(this.context, message, duration).show()
}