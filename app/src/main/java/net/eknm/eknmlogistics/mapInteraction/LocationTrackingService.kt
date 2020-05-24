package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import net.eknm.eknmlogistics.android.ioToIo
import net.eknm.eknmlogistics.android.ioToMain

class LocationTrackingService private constructor(
    private val locationProvider: LocationProvider,
    private val lazyMap: Single<GoogleMap>
) {
    private val locationProcessor = BehaviorProcessor.create<Location>()

    private val mapCameraState = PublishProcessor.create<MapCameraState>()
    private val mapCameraIdle = PublishProcessor.create<Unit>()

    private lateinit var googleMap: GoogleMap

    fun trackLocation(): Flowable<Location> = locationProcessor

    fun loadLastLocation(): Single<Location> = locationProcessor.take(1).firstOrError()

    fun trackMapCameraIdleEvents() = mapCameraIdle.hide()

    fun trackMapCameraState() = mapCameraState.hide()

    @SuppressLint("CheckResult")
    private fun start() {
        locationProvider
            .subscribeToLocation()
            .subscribe { location ->
                locationProcessor.onNext(location)
            }
        lazyMap
            .ioToMain()
            .subscribe { map ->
                onMapLoaded(map)
            }
    }

    private fun onMapLoaded(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnCameraMoveListener { handleMapMovement() }
        googleMap.setOnCameraIdleListener { mapCameraIdle.onNext(Unit) }
    }

    private fun handleMapMovement() {
        val mapCenter = googleMap.cameraPosition.target.let { Location(it.latitude, it.longitude) }
        val mapZoom = googleMap.cameraPosition.zoom

        mapCameraState.onNext(MapCameraState(mapCenter, mapZoom))
    }

    companion object {
        fun create(
            locationProvider: LocationProvider,
            lazyMap: Single<GoogleMap>
        ): LocationTrackingService {
            return LocationTrackingService(locationProvider, lazyMap).apply {
                start()
            }
        }
    }
}