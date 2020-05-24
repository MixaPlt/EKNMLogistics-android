package net.eknm.eknmlogistics.homeFlow.homeFragment

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.mapInteraction.CenterLocationService
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val centerLocationService: CenterLocationService
) : BaseFragmentViewModel() {

    private val _createOrderEvent = SingleLiveEvent<Unit>()
    val createOrderEvent: LiveData<Unit> = _createOrderEvent
    fun createOrder() {
        _createOrderEvent.call()
    }

    fun centerLocation() {
        centerLocationService.centerLocation()
    }
}