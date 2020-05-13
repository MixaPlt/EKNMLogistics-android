package net.eknm.eknmlogistics.authorization.authorizationRepository

import net.eknm.eknmlogistics.api.UserApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepository @Inject constructor(
    private val userApi: UserApi
) {
}