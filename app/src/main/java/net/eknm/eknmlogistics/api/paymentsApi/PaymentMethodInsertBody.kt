package net.eknm.eknmlogistics.api.paymentsApi

import com.google.gson.annotations.SerializedName

data class PaymentMethodInsertBody(
    val title: String,
    @SerializedName("payment_id")
    val paymentId: String,
    @SerializedName("payment_service")
    val paymentService: String
)