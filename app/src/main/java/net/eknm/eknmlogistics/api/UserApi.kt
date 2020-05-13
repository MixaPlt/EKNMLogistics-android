package net.eknm.eknmlogistics.api

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface UserApi {
    @POST("")
    fun registerUser()

    companion object {
        fun newInstance(okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
            return retrofit.create<UserApi>(
                UserApi::class.java
            )
        }
    }
}