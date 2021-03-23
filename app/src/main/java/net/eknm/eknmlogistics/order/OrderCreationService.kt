package net.eknm.eknmlogistics.order

import io.reactivex.Single
import net.eknm.eknmlogistics.api.mapsApi.MapsRepository
import net.eknm.eknmlogistics.mapInteraction.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCreationService @Inject constructor(
    private val mapsRepository: MapsRepository
) {
    private var origin: Location? = null
    private var destination: Location? = null

    fun setOrigin(origin: Location) {
        this.origin = origin
    }

    fun setDestination(destination: Location) {
        this.destination = destination
    }

    fun loadOriginAddress(): Single<String> = mapsRepository.reverseGeocode(origin!!)
    fun loadDestinationAddress(): Single<String> = mapsRepository.reverseGeocode(destination!!)
}