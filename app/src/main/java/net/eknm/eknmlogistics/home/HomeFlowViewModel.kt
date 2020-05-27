package net.eknm.eknmlogistics.home

import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.mapInteraction.CenterLocationService
import javax.inject.Inject

class HomeFlowViewModel @Inject constructor(
    private val centerLocationService: CenterLocationService
) : BaseFlowViewModel() {
    fun centerLocation() {
        centerLocationService.centerLocation()
    }
}