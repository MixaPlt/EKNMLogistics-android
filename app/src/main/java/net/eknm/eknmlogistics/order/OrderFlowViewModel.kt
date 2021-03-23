package net.eknm.eknmlogistics.order

import androidx.lifecycle.LiveData
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
    private val mapsRepository: MapsRepository,
    private val orderCreationService: OrderCreationService
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
            orderCreationService.setOrigin(location)
            _showEndDestinationFragmentEvent.call()
        } else {
            endLocation = location
            orderCreationService.setDestination(location)

            executeDisposable {
                mapsRepository
                    .createRoute(startLocation!!, endLocation!!)
                    .onErrorReturn {
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
}