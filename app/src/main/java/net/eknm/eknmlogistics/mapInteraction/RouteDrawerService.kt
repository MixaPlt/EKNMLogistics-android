package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import io.reactivex.Single
import net.eknm.eknmlogistics.root.RootScope
import javax.inject.Inject

@RootScope
class RouteDrawerService @Inject constructor(
    private val lazyMap: Single<GoogleMap>
) {
    @SuppressLint("CheckResult")
    fun drawRoute(
        route: Route
    ) {
        val options = PolylineOptions()
            .width(16f)
            .geodesic(false)
        for (point in route.points) {
            options.add(LatLng(point.latitude, point.longitude))
        }
        lazyMap
           .subscribe { map ->
                map.addPolyline(options)
            }
    }
}