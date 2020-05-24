package net.eknm.eknmlogistics.mapInteraction

data class Location(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float = 0f
) {
    companion object {
        fun fromAndroidLocation(androidLocation: android.location.Location): Location {
            return Location(
                androidLocation.latitude,
                androidLocation.longitude,
                androidLocation.accuracy
            )
        }
    }
}