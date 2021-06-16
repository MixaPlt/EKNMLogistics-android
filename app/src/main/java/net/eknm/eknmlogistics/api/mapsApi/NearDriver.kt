package net.eknm.eknmlogistics.api.mapsApi

import com.google.gson.annotations.SerializedName
import net.eknm.eknmlogistics.mapInteraction.Location

data class NearDriver constructor(
    val id: Int,
    val location: Location,
    @SerializedName("is_your_driver")
    val isYourDriver: Boolean
)
