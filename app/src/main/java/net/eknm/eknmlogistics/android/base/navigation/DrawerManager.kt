package net.eknm.eknmlogistics.android.base.navigation

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent

interface DrawerManager {
    val openDrawerEvent: LiveData<Unit>
    fun openDrawer()
}

class DrawerManagerImpl : DrawerManager {
    private val _openDrawerEvent = SingleLiveEvent<Unit>()
    override val openDrawerEvent = _openDrawerEvent

    override fun openDrawer() {
        _openDrawerEvent.call()
    }
}

