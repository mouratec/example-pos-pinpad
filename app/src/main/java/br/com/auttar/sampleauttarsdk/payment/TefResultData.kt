package br.com.auttar.sampleauttarsdk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TefResultData(
    val transactionCode: Int,
    val amount: String,
    val nsuCTF: Int,
    val dateSdk: String,
    val display: String,
    val messageAction: String,
    val authorizedNSU: String,
    val codSupport: String,
    val transactionId: String,
    val receiverPSP: String,
) : Parcelable
