package net.eknm.eknmlogistics.authorization

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.eknm.eknmlogistics.authorization.loginFragment.LoginFragment
import net.eknm.eknmlogistics.authorization.loginRegisterFragment.LoginRegisterFragment
import net.eknm.eknmlogistics.authorization.registerFragment.RegisterFragment

@Module
interface LoginActivityModule {

    @ContributesAndroidInjector
    fun loginRegisterFragment(): LoginRegisterFragment

    @ContributesAndroidInjector
    fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun registerFragment(): RegisterFragment
}