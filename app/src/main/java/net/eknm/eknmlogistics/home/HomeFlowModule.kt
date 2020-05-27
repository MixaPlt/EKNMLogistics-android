package net.eknm.eknmlogistics.home

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.home.homeFragment.HomeFragment

@Module
interface HomeFlowModule {

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment
}