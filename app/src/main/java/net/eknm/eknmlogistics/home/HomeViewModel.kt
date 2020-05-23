package net.eknm.eknmlogistics.home

import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.base.navigation.BaseViewModel
import net.eknm.eknmlogistics.home.mapInteraction.CenterLocationService
import net.eknm.eknmlogistics.home.mapInteraction.LocationTrackingService
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val locationTrackingService: LocationTrackingService,
    private val centerLocationService: CenterLocationService
): BaseFragmentViewModel() {

    fun centerLocation() {
        centerLocationService.centerLocation()
    }
}