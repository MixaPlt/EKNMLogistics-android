package net.eknm.eknmlogistics.orderFlow.destinationFragment

import androidx.databinding.ObservableField
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.mapInteraction.LocationTrackingService
import javax.inject.Inject

class DestinationViewModel @Inject constructor(
    private val locationTrackingService: LocationTrackingService
): BaseFragmentViewModel() {
    val title = ObservableField<String>("Select destination")

    override fun onInitialize() {
        locationTrackingService
            .trackMapCameraIdleEvents()
            .ioToMain()

    }
}