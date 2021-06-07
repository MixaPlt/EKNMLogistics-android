package net.eknm.eknmlogistics.root

import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import net.eknm.eknmlogistics.EknmLogisticsApplication
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepositoryImpl
import net.eknm.eknmlogistics.authorization.authorizationRepository.LocalUserSource
import net.eknm.eknmlogistics.mapInteraction.LocationProvider
import net.eknm.eknmlogistics.mapInteraction.LocationTrackingService
import javax.inject.Singleton

@Module(includes = [RepositoryModule.Impl::class])
interface RepositoryModule {

    @Binds
    fun localUserSource(application: EknmLogisticsApplication): LocalUserSource

    @Binds
    @Singleton
    abstract fun provideAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Module
    class Impl {
        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(application: EknmLogisticsApplication): FusedLocationProviderClient {
            return FusedLocationProviderClient(application)
        }
    }
}
