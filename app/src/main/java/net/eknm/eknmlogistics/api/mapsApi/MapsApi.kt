package net.eknm.eknmlogistics.api.mapsApi

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApi {
    @GET("reverse_geocode")
    fun reverseGeocode(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Single<String>

    companion object {
        fun newInstance(okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): MapsApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
            return retrofit.create<MapsApi>(
                MapsApi::class.java
            )
        }
    }
}