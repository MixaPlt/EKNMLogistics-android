package net.eknm.eknmlogistics.authorization.authorizationRepository

import io.reactivex.Single
import net.eknm.eknmlogistics.android.toMD5
import net.eknm.eknmlogistics.api.userApi.UserApi
import net.eknm.eknmlogistics.api.userApi.UserRegistrationRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepository @Inject constructor(
    private val userApi: UserApi
) {
    fun registerUser(email: String, name: String, password: String): Single<Unit> {
        val body = UserRegistrationRequestBody(name, email, password.toMD5())
        return userApi.registerUser(body)
    }
}