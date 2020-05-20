package net.eknm.eknmlogistics.authorization.authorizationRepository

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import net.eknm.eknmlogistics.android.Optional
import net.eknm.eknmlogistics.android.ioToIo
import net.eknm.eknmlogistics.android.toMD5
import net.eknm.eknmlogistics.api.userApi.LoginBody
import net.eknm.eknmlogistics.api.userApi.UserApi
import net.eknm.eknmlogistics.api.userApi.UserRegistrationRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepository @Inject constructor(
    private val userApi: UserApi,
    private val localUserSource: LocalUserSource
) {
    private val userProcessor = BehaviorProcessor.create<Optional<User>>()
    val user get() = userProcessor.value?.item

    fun registerUser(email: String, name: String, password: String): Single<User> {
        val body = UserRegistrationRequestBody(name, email, password.toMD5())
        return userApi.registerUser(body).doOnSuccess { onUserUpdated(it) }
    }

    fun trackSession() = localUserSource.trackUserToken()

    fun logOut() {
        localUserSource.saveUserToken(null)
        onUserUpdated(null)
    }

    fun logIn(email: String, password: String): Single<User> {
        return userApi
            .logIn(LoginBody(email, password.toMD5()))
            .doOnSuccess { onUserUpdated(it) }
    }

    fun trackUser(): Flowable<Optional<User>> {
        updateUser()
        return userProcessor
    }

    @SuppressLint("CheckResult")
    private fun updateUser() {
        userApi
            .getOwnProfile()
            .ioToIo()
            .subscribe({
                onUserUpdated(it)
            }, {})
    }

    private fun onUserUpdated(newUser: User?) {
        userProcessor.onNext(Optional(newUser))
    }
}