package net.eknm.eknmlogistics.home.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor

class LocationTrackingService private constructor(
    private val locationProvider: LocationProvider
) {
    private val locationProcessor = BehaviorProcessor.create<Location>()

    fun trackLocation(): Flowable<Location> = locationProcessor

    fun loadLastLocation(): Single<Location> = locationProcessor.take(1).firstOrError()

    @SuppressLint("CheckResult")
    private fun start() {
        locationProvider
            .subscribeToLocation()
            .subscribe { location ->
                locationProcessor.onNext(location)
            }
    }

    companion object {
        fun create(
            locationProvider: LocationProvider
        ): LocationTrackingService {
            return LocationTrackingService(locationProvider).apply {
                start()
            }
        }
    }
}