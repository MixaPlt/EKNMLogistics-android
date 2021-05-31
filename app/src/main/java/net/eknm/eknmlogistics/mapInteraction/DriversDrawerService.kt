package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.singleThreadLazy
import net.eknm.eknmlogistics.api.mapsApi.MapsApi
import java.util.concurrent.TimeUnit

class DriversDrawerService private constructor(
    private val mapsApi: MapsApi,
    private val mapMarkerDrawerService: MapMarkerDrawerService,
    private val resources: Resources
) {
    private val carBitmap by singleThreadLazy {
        val drawable = resources.getDrawable(R.drawable.ic_car_marker)
        drawable.toBitmap()
    }

    @SuppressLint("CheckResult")
    private fun start() {
        Flowable.interval(UPDATE_PERIOD_MILLS, TimeUnit.MILLISECONDS)
            .concatMapSingle {
                mapsApi.nearDrivers()
                    .onErrorReturnItem(emptyList())
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEachIndexed { index, location ->
                    mapMarkerDrawerService.addMarker("Driver$index", carBitmap, location)
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

