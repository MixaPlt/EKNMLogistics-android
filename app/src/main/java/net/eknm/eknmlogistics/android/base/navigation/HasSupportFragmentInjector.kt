package net.eknm.eknmlogistics.android.base.navigation

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector

interface HasSupportFragmentInjector {
    fun supportFragmentInjector(): AndroidInjector<Fragment>
}