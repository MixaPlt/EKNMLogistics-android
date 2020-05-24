package net.eknm.eknmlogistics.orderFlow

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.orderFlow.destinationFragment.DestinationFragment

@Module
interface OrderFlowModule {
    @ContributesAndroidInjector
    fun destinationFragment(): DestinationFragment
}