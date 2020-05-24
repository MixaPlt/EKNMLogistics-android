package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import net.eknm.eknmlogistics.root.RootScope
import javax.inject.Inject

@RootScope
class CenterLocationService @Inject constructor(
    private val locationTrackingService: LocationTrackingService,
    private val lazyMap: Single<GoogleMap>
) {
    @SuppressLint("CheckResult")
    fun centerLocation() {
        Single.zip(
            lazyMap,
            locationTrackingService.loadLastLocation(),
            BiFunction { map: GoogleMap, location: Location ->
                centerLocation(map, location)
            })
            .subscribe()

    }

    fun centerLocation(map: GoogleMap, location: Location) {
        centerLocation(map, location, DEFAULT_ZOOM)
    }

    fun centerLocation(
        map: GoogleMap,
        location: Location,
        zoomLevel: Double
    ) {
        val latLng = LatLng(location.latitude, location.longitude)

        val cameraPosition = CameraPosition.fromLatLngZoom(latLng, zoomLevel.toFloat())
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.animateCamera(cameraUpdate)
    }

    companion object {
        private const val DEFAULT_ZOOM = 15.0
        private const val ANIM_TIME = 300
    }
}