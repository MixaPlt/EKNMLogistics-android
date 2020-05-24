package net.eknm.eknmlogistics.payments

import com.google.gson.annotations.SerializedName

data class PaymentMethod(
    val title: String,
    @SerializedName("payment_service")
    val paymentService: String,
    val image: String
)