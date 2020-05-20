package net.eknm.eknmlogistics.authorization.authorizationRepository

import io.reactivex.Flowable
import net.eknm.eknmlogistics.android.Optional

interface LocalUserSource {
    fun saveUserToken(token: String?)
    fun trackUserToken(): Flowable<Optional<String>>
    fun getUserToken(): String?
}