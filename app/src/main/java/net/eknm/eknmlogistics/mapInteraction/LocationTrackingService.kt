package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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

    private val mapCameraState = BehaviorProcessor.create<MapCameraState>()
    private val mapCameraIdle = PublishProcessor.create<Unit>()

    private lateinit var googleMap: GoogleMap

    val lastKnownLocation get() = locationProcessor.value

    val lastMapCameraState get() = mapCameraState.value

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

        val cameraPosition = CameraPosition.fromLatLngZoom(LatLng(49.988358, 36.232845), 10f)
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap.moveCamera(cameraUpdate)
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