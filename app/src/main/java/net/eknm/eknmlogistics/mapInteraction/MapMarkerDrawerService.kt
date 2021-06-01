package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MapMarkerDrawerService @Inject constructor(
    private val lazyMap: Single<GoogleMap>
) {
    private val markers = HashMap<String, Marker>()

    @SuppressLint("CheckResult")
    fun addMarker(tag: String, bitmap: Bitmap, location: Location) {
        lazyMap
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { map ->
                val previousMarker = markers[tag]
                val markerOptions = MarkerOptions().apply {
                    position(LatLng(location.latitude, location.longitude))
                    icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    anchor(0.5f, 0.5f)
                }
                val marker = map.addMarker(markerOptions)
                marker.tag = tag
                markers[tag] = marker
                previousMarker?.remove()
            }
    }
}