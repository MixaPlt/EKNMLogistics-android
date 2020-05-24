package net.eknm.eknmlogistics.api.mapsApi

import io.reactivex.Single
import net.eknm.eknmlogistics.android.ioToIo
import net.eknm.eknmlogistics.mapInteraction.Location
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
}