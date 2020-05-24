package net.eknm.eknmlogistics.homeFlow

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.homeFlow.homeFragment.HomeFragment

@Module
interface HomeFlowModule {

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment
}