package net.eknm.eknmlogistics.root

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.authorization.LoginActivity
import net.eknm.eknmlogistics.authorization.LoginActivityModule

@Module
interface ActivityProviderModule {

    @ContributesAndroidInjector(modules = [RootActivityModule::class])
    fun rootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    fun loginActivity(): LoginActivity
}