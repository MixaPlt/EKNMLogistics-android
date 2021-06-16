package net.eknm.eknmlogistics.mapInteraction

data class DriverOnMap(
    val latitude: Float,
    val longitude: Float,
    val accuracy: Float,
    val direction: Float,
    val isYourDriver: Boolean
) {
    val location = Location(latitude.toDouble(), longitude.toDouble(), accuracy)
}
