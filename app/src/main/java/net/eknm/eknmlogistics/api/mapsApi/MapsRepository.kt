package net.eknm.eknmlogistics.api.mapsApi

import io.reactivex.Single
import net.eknm.eknmlogistics.android.ioToIo
import net.eknm.eknmlogistics.mapInteraction.Location
import net.eknm.eknmlogistics.mapInteraction.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapsRepository @Inject constructor(
    private val mapsApi: MapsApi
) {
    fun reverseGeocode(location: Location): Single<String> {
        return mapsApi
            .reverseGeocode(location.latitude, location.longitude)
            .ioToIo()
    }

    fun createRoute(origin: Location, destination: Location): Single<Route> {
        return mapsApi
            .createRoute(origin.latitude, origin.longitude, destination.latitude, destination.longitude)
            .ioToIo()
    }
}