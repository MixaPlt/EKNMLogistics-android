package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.core.graphics.drawable.toBitmap
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.ValueStreamAnimator
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.api.mapsApi.MapsApi
import java.util.concurrent.TimeUnit
import kotlin.math.*


class DriversDrawerService private constructor(
    private val mapsApi: MapsApi,
    private val mapMarkerDrawerService: MapMarkerDrawerService,
    private val resources: Resources
) {
    private val carBitmap by singleThreadLazy {
        val drawable = resources.getDrawable(R.drawable.ic_car_marker)
        drawable.toBitmap()
    }
    val fractionPerMillisecond = 0.01f

    val floatLogarithmicInterpolator = { currentValue: Float, targetValue: Float, timeMills: Long ->
        val difference = targetValue - currentValue
        targetValue - difference * (1 - fractionPerMillisecond).pow(timeMills.toInt())
    }

    val driverStateLogarithmicInterpolator =
        { currentValue: DriverOnMap, targetValue: DriverOnMap, timeMills: Long ->
            DriverOnMap(
                floatLogarithmicInterpolator(
                    currentValue.latitude,
                    targetValue.latitude,
                    timeMills
                ),
                floatLogarithmicInterpolator(
                    currentValue.longitude,
                    targetValue.longitude,
                    timeMills
                ),
                floatLogarithmicInterpolator(
                    currentValue.accuracy,
                    targetValue.accuracy,
                    timeMills
                ),
                floatLogarithmicInterpolator(
                    currentValue.direction,
                    targetValue.direction,
                    timeMills
                )
            )
        }

    private val driverStateAnimators = HashMap<Int, ValueStreamAnimator<DriverOnMap>>()

    private fun onNewLocation(id: Int, location: Location) {
        val previousLocation = driverStateAnimators[id]?.targetValue?.location
        val angle = if (previousLocation != null) {
            val previousAngle = driverStateAnimators[id]!!.targetValue.direction
            val deltaLat = location.latitude - previousLocation.latitude
            val deltaLng = location.longitude - previousLocation.longitude
            val angle = -atan(deltaLat / deltaLng) + if (deltaLng < 0) PI else .0 //[-PI/2 - 3PI/2]
            if (abs(angle - previousAngle) < abs(angle - 2 * PI - previousAngle)) angle else angle - 2 * PI
        } else .0
        val driverState = DriverOnMap(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            location.accuracy,
            angle.toFloat()
        )
        if (!driverStateAnimators.containsKey(id)) {
            driverStateAnimators[id] = ValueStreamAnimator(
                driverState,
                { updateMarker(id, it) },
                driverStateLogarithmicInterpolator
            )
            driverStateAnimators[id]!!.start()
        }
        driverStateAnimators[id]!!.targetValue = driverState
    }

    fun updateMarker(id: Int, carState: DriverOnMap) {
        val matrix = Matrix()
        matrix.postRotate(carState.direction * 57.3f)
        val rotatedBitmap =
            Bitmap.createBitmap(carBitmap, 0, 0, carBitmap.width, carBitmap.height, matrix, true)
        mapMarkerDrawerService.addMarker("Driver$id", rotatedBitmap, carState.location)
    }

    @SuppressLint("CheckResult")
    private fun start() {
        Flowable.interval(UPDATE_PERIOD_MILLS, TimeUnit.MILLISECONDS)
            .concatMapSingle {
                mapsApi.nearDrivers()
                    .onErrorReturnItem(emptyList())
            }
            .onBackpressureLatest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEachIndexed { index, location ->
                    //TODO: Use driver id
                    onNewLocation(index, location)
                }
            }
    }

    companion object {
        const val UPDATE_PERIOD_MILLS = 700L
        fun create(
            mapsApi: MapsApi,
            mapMarkerDrawerService: MapMarkerDrawerService,
            resources: Resources
        ): DriversDrawerService {
            return DriversDrawerService(mapsApi, mapMarkerDrawerService, resources).apply {
                start()
            }
        }
    }
}

