package net.eknm.eknmlogistics.order.destinationFragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.api.mapsApi.MapsRepository
import net.eknm.eknmlogistics.mapInteraction.Location
import net.eknm.eknmlogistics.mapInteraction.LocationTrackingService
import javax.inject.Inject

class DestinationViewModel @Inject constructor(
    private val locationTrackingService: LocationTrackingService,
    private val mapsRepository: MapsRepository
) : BaseFragmentViewModel() {

    private val _addressText = MutableLiveData<String>()
    val addressText: LiveData<String> = _addressText

    private val _destinationSelectedEvent = SingleLiveEvent<Location>()
    val destinationSelected: LiveData<Location> = _destinationSelectedEvent

    val title = ObservableField<String>("Select destination")

    override fun onInitialize() {
        updateAddress()
        executeDisposable {
            locationTrackingService
                .trackMapCameraIdleEvents()
                .subscribe({
                    updateAddress()
                }, {})
        }
    }

    fun onLocationSelected() {
        _destinationSelectedEvent.value = locationTrackingService.lastMapCameraState!!.location
    }

    private fun updateAddress() {
        executeDisposable {
            mapsRepository
                .reverseGeocode(locationTrackingService.lastMapCameraState!!.location)
                .ioToMain()
                .subscribe({
                    _addressText.value = it
                }, {})
        }
    }
}