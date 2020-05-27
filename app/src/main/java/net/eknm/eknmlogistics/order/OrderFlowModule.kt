package net.eknm.eknmlogistics.order

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.order.destinationFragment.DestinationFragment

@Module
interface OrderFlowModule {
    @ContributesAndroidInjector
    fun destinationFragment(): DestinationFragment
}