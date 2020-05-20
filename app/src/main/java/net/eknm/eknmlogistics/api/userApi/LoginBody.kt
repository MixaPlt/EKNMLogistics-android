package net.eknm.eknmlogistics.api.userApi

data class LoginBody(
    val email: String,
    val password_hash: String
)