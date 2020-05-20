package net.eknm.eknmlogistics.api

import net.eknm.eknmlogistics.authorization.authorizationRepository.LocalUserSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoginInterceptor constructor(private val localUserSource: LocalUserSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = processRequest(request)
        var response = chain.proceed(request)
        response = processResponse(response)
        return response
    }

    private fun processRequest(request: Request): Request {
        val token = localUserSource.getUserToken() ?: return request
        return request.newBuilder().addHeader(HEADER_SESSION_TOKEN, token).build()
    }

    private fun processResponse(response: Response): Response {
        response.header(HEADER_SESSION_TOKEN)?.let {
            localUserSource.saveUserToken(it)
        }
        return response
    }

    companion object {
        private const val HEADER_SESSION_TOKEN = "session-token"
    }
}