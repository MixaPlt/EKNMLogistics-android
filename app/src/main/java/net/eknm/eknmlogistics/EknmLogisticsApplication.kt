package net.eknm.eknmlogistics

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.root.DaggerAppComponent

class EknmLogisticsApplication : DaggerApplication() {
    private val appComponent by singleThreadLazy {
        DaggerAppComponent.builder()
            .application(this)
            .build().apply {
                inject(this@EknmLogisticsApplication)
            }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    companion object
}