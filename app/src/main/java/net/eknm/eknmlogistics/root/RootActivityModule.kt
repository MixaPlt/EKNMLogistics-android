package net.eknm.eknmlogistics.root

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.login.LoginFragment

@Module
interface RootActivityModule {
    @ContributesAndroidInjector
    fun loginFragment(): LoginFragment
}