package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.graphics.drawable.toBitmap
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.ValueStreamAnimator
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.api.mapsApi.MapsApi
import net.eknm.eknmlogistics.api.mapsApi.NearDriver
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

    private val yourCarBitmap by singleThreadLazy {
        val drawable = resources.getDrawable(R.drawable.ic_your_car_marker)
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
                ),
                targetValue.isYourDriver
            )
        }

    private val driverStateAnimators = HashMap<Int, ValueStreamAnimator<DriverOnMap>>()

    private fun onNewLocation(driver: NearDriver) {
        val id = driver.id
        val location = driver.location

        val previousLocation = driverStateAnimators[id]?.targetValue?.location
        val angle = if (previousLocation != null) {
            val previousAngle = driverStateAnimators[id]!!.targetValue.direction
            val deltaLat = location.latitude - previousLocation.latitude
            val deltaLng = location.longitude - previousLocation.longitude
            var angle = -atan(deltaLat / deltaLng) + if (deltaLng < 0) PI else .0 //[-PI/2 - 3PI/2]
            if (angle.isNaN()) {
                angle = PI / 2 * sign(deltaLat)
            }
            if (abs(angle - previousAngle) < abs(angle - 2 * PI - previousAngle)) angle else angle - 2 * PI
        } else .0
        val driverState = DriverOnMap(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            location.accuracy,
            angle.toFloat(),
            driver.isYourDriver
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
        Color.BLACK
        val bitmap = if (carState.isYourDriver) tintImage(carBitmap, id % 256 + 0xFFEE0000) else carBitmap

        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
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
                it.forEach { driver ->
                    onNewLocation(driver)
                }
            }
    }

    fun tintImage(bitmap: Bitmap, color: Long): Bitmap {
        val paint = Paint()
        paint.setColorFilter(PorterDuffColorFilter(color.toInt(), PorterDuff.Mode.SRC_IN))
        val bitmapResult = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapResult)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return bitmapResult
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

