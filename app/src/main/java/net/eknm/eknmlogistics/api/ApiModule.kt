package net.eknm.eknmlogistics.api

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import net.eknm.eknmlogistics.BuildConfig
import net.eknm.eknmlogistics.EknmLogisticsApplication
import net.eknm.eknmlogistics.api.mapsApi.MapsApi
import net.eknm.eknmlogistics.api.paymentsApi.PaymentsApi
import net.eknm.eknmlogistics.api.userApi.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(application: EknmLogisticsApplication): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val loginInterceptor = LoginInterceptor(application)
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(loginInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient, gson: Gson): UserApi {
        return UserApi.newInstance(okHttpClient, BuildConfig.API_URL + "user/", gson)
    }

    @Provides
    @Singleton
    fun providePaymentsApi(okHttpClient: OkHttpClient, gson: Gson): PaymentsApi {
        return PaymentsApi.newInstance(okHttpClient, BuildConfig.API_URL + "payments/", gson)
    }

    @Provides
    @Singleton
    fun provideMapsApi(okHttpClient: OkHttpClient, gson: Gson): MapsApi {
        return MapsApi.newInstance(okHttpClient, BuildConfig.API_URL + "maps/", gson)
    }
}