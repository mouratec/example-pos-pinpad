package br.com.auttar.sampleauttarsdk.payment.enums

import androidx.annotation.StringRes
import br.com.auttar.sampleauttarsdk.R


enum class ContactlessStatusEnum(@StringRes val stringResId: Int) {
    BEGIN(R.string.contact_less_status_begin),
    PROCESSING(R.string.contact_less_status_processing),
    DONE(R.string.contact_less_status_done),
    ERROR(R.string.contact_less_status_error);
}