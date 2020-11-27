package net.eknm.eknmlogistics.root

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.home.HomeFlowFragment
import net.eknm.eknmlogistics.home.HomeFlowModule
import net.eknm.eknmlogistics.mapInteraction.CenterLocationService
import net.eknm.eknmlogistics.mapInteraction.LocationProvider
import net.eknm.eknmlogistics.mapInteraction.LocationTrackingService
import net.eknm.eknmlogistics.mapInteraction.RouteDrawerService
import net.eknm.eknmlogistics.order.OrderFlowFragment
import net.eknm.eknmlogistics.order.OrderFlowModule
import net.eknm.eknmlogistics.payments.PaymentsFlowFragment
import net.eknm.eknmlogistics.payments.PaymentsFlowModule

@Module(includes = [RootActivityModule.Impl::class])
interface RootActivityModule {

    @ContributesAndroidInjector(modules = [HomeFlowModule::class])
    fun homeFlowFragment(): HomeFlowFragment

    @ContributesAndroidInjector(modules = [PaymentsFlowModule::class])
    fun paymentsFlow(): PaymentsFlowFragment

    @ContributesAndroidInjector(modules = [OrderFlowModule::class])
    fun orderFlowFragment(): OrderFlowFragment

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

        @Provides
        @RootScope
        fun provideLocationTrackingService(
            locationProvider: LocationProvider,
            rootActivity: RootActivity
        ): LocationTrackingService {
            return LocationTrackingService.create(locationProvider, rootActivity.mapSingle)
        }

        @Provides
        @RootScope
        fun provideRouteDrawerService(
            rootActivity: RootActivity
        ): RouteDrawerService {
            return RouteDrawerService(rootActivity.mapSingle)
        }
    }
}