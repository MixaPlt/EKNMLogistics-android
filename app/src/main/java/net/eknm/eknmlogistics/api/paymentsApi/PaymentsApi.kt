package net.eknm.eknmlogistics.api.paymentsApi

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import net.eknm.eknmlogistics.authorization.authorizationRepository.User
import net.eknm.eknmlogistics.payments.PaymentMethod
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentsApi {
    @GET(".")
    fun getMyPaymentMethods(): Single<List<PaymentMethod>>

    @POST(".")
    fun addPaymentMethod(@Body paymentMethod: PaymentMethodInsertBody): Single<Unit>

    companion object {
        fun newInstance(okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): PaymentsApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
            return retrofit.create<PaymentsApi>(
                PaymentsApi::class.java
            )
        }
    }
}