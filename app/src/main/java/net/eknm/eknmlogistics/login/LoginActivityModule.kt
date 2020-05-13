package net.eknm.eknmlogistics.login

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.login.loginFragment.LoginFragment
import net.eknm.eknmlogistics.login.loginRegisterFragment.LoginRegisterFragment

@Module
interface LoginActivityModule {

    @ContributesAndroidInjector
    fun loginRegisterFragment(): LoginRegisterFragment

    @ContributesAndroidInjector
    fun loginFragment(): LoginFragment
}