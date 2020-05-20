package net.eknm.eknmlogistics.api.userApi

import com.google.gson.annotations.SerializedName

data class UserRegistrationRequestBody(
    val name: String,
    val email: String,
    @SerializedName("password_hash")
    val passwordHash: String
)