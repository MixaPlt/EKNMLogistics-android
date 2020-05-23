package net.eknm.eknmlogistics.root

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import net.eknm.eknmlogistics.EknmLogisticsApplication
import net.eknm.eknmlogistics.authorization.authorizationRepository.LocalUserSource
import net.eknm.eknmlogistics.home.mapInteraction.LocationProvider
import net.eknm.eknmlogistics.home.mapInteraction.LocationTrackingService
import javax.inject.Singleton

@Module(includes = [RepositoryModule.Impl::class])
interface RepositoryModule {

    @Binds
    fun localUserSource(application: EknmLogisticsApplication): LocalUserSource

    @Module
    class Impl {
        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(application: EknmLogisticsApplication): FusedLocationProviderClient {
            return FusedLocationProviderClient(application)
        }

        @Provides
        @Singleton
        fun provideLocationRepository(locationProvider: LocationProvider): LocationTrackingService {
            return LocationTrackingService.create(locationProvider)
        }
    }
}