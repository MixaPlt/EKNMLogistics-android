package net.eknm.eknmlogistics.order

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.order.destinationFragment.DestinationFragment
import net.eknm.eknmlogistics.order.orderOverviewFragment.OrderOverviewFragment

@Module
interface OrderFlowModule {
    @ContributesAndroidInjector
    fun destinationFragment(): DestinationFragment

    @ContributesAndroidInjector
    fun orderOverviewFragment(): OrderOverviewFragment
}