package net.eknm.eknmlogistics.home.mapInteraction

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class LocationProvider @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    var lastKnownLocation: Location? = null

    fun subscribeToLocation(): Flowable<Location> = Flowable.create({ emitter ->
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val periodicLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val lastLocation =
                    locationResult?.lastLocation?.let { Location.fromAndroidLocation(it) } ?: return
                lastKnownLocation = lastLocation
                emitter.onNext(lastLocation)
            }
        }

        fusedLocationProviderClient
            .requestLocationUpdates(locationRequest, periodicLocationCallback, null)

        emitter.setCancellable {
            fusedLocationProviderClient.removeLocationUpdates(periodicLocationCallback)
        }
    }, BackpressureStrategy.LATEST)
}