package net.eknm.eknmlogistics.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.mapInteraction.Location
import net.eknm.eknmlogistics.mapInteraction.Route
import net.eknm.eknmlogistics.mapInteraction.RouteDrawerService
import javax.inject.Inject

class OrderFlowViewModel @Inject constructor(
    private val routeDrawerService: RouteDrawerService
) : BaseFlowViewModel() {
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
            val lineRoute = Route(listOf(startLocation!!, endLocation!!))
            routeDrawerService.drawRoute(lineRoute)
        }
    }
}