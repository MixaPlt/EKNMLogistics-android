package net.eknm.eknmlogistics.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.mapInteraction.Location
import javax.inject.Inject

class OrderFlowViewModel @Inject constructor() : BaseFlowViewModel() {
    private var startLocation: Location? = null
    private var endLocation: Location? = null

    private val _showEndDestinationFragmentEvent = SingleLiveEvent<Unit>()
    val showEndDestinationFragmentEvent: LiveData<Unit> = _showEndDestinationFragmentEvent

    fun onNewLocation(location: Location) {
        if (startLocation == null) {
            startLocation = location
            _showEndDestinationFragmentEvent.call()
        } else {
            endLocation = location
        }
    }
}