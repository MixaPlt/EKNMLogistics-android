package net.eknm.eknmlogistics.api.mapsApi

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import net.eknm.eknmlogistics.mapInteraction.Route
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MapsApi {
    @GET("reverse_geocode")
    fun reverseGeocode(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Single<String>

    @POST("create_route")
    fun createRoute(
        @Query("origin_lat") origin_lat: Double,
        @Query("origin_lng") origin_lng: Double,
        @Query("destination_lat") destination_lat: Double,
        @Query("destination_lng") destination_lng: Double
    ): Single<Route>

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