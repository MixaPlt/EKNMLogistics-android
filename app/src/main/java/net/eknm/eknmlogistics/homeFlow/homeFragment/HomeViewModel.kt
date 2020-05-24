package net.eknm.eknmlogistics.homeFlow.homeFragment

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : BaseFragmentViewModel() {

    private val _createOrderEvent = SingleLiveEvent<Unit>()
    val createOrderEvent: LiveData<Unit> = _createOrderEvent
    fun createOrder() {
        _createOrderEvent.call()
    }
}