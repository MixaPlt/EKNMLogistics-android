package net.eknm.eknmlogistics.root

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.home.HomeFragment
import net.eknm.eknmlogistics.home.mapInteraction.CenterLocationService
import net.eknm.eknmlogistics.home.mapInteraction.LocationTrackingService

@Module(includes = [RootActivityModule.Impl::class])
interface RootActivityModule {

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment

    @Module
    class Impl {
        @Provides
        @RootScope
        fun provideCenterLocationService(
            locationTrackingService: LocationTrackingService,
            rootActivity: RootActivity
        ): CenterLocationService {
            return CenterLocationService(locationTrackingService, rootActivity.mapSingle)
        }
    }
}