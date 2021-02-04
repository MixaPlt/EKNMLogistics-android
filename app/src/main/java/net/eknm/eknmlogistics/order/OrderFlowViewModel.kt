package net.eknm.eknmlogistics.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.api.mapsApi.MapsRepository
import net.eknm.eknmlogistics.mapInteraction.Location
import net.eknm.eknmlogistics.mapInteraction.Route
import net.eknm.eknmlogistics.mapInteraction.RouteDrawerService
import javax.inject.Inject

class OrderFlowViewModel @Inject constructor(
    private val routeDrawerService: RouteDrawerService,
    private val mapsRepository: MapsRepository
) : BaseFlowViewModel() {
    private var startLocation: Location? = null
    private var endLocation: Location? = null

    private val _showEndDestinationFragmentEvent = SingleLiveEvent<Unit>()
    val showEndDestinationFragmentEvent: LiveData<Unit> = _showEndDestinationFragmentEvent

    private val _showOrderOverviewFragment = SingleLiveEvent<Unit>()
    val showOrderOverviewFragment: LiveData<Unit> = _showOrderOverviewFragment

    fun onNewLocation(location: Location) {
        if (startLocation == null) {
            startLocation = location
            _showEndDestinationFragmentEvent.call()
        } else {
            endLocation = location

            executeDisposable {
                mapsRepository
                    .createRoute(startLocation!!, endLocation!!)
                    .onErrorReturn {
                        it
                        (Route(listOf(startLocation!!, endLocation!!)))
                    }
                    .ioToMain()
                    .subscribe { route ->
                        routeDrawerService.clearRoutes()
                        routeDrawerService.drawRoute(route)
                        _showOrderOverviewFragment.call()
                    }
            }
        }
    }

    companion object {
        const val TAG = "eknmlogistics.order"
    }
}