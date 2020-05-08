package net.eknm.eknmlogistics.root

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityProviderModule {

    @ContributesAndroidInjector(modules = [RootActivityModule::class])
    fun rootActivity(): RootActivity
}