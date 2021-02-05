package net.eknm.eknmlogistics.home

import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.mapInteraction.CenterLocationService
import net.eknm.eknmlogistics.mapInteraction.MapPaddingManager
import javax.inject.Inject

class HomeFlowViewModel @Inject constructor(
    private val centerLocationService: CenterLocationService,
    private val mapPaddingManager: MapPaddingManager
) : BaseFlowViewModel() {
    fun centerLocation() {
        centerLocationService.centerLocation()
    }

    fun setBottomPadding(paddingBottom: Int) {
        mapPaddingManager.setPaddingBottom(paddingBottom)
    }
}