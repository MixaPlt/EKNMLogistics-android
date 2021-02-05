package net.eknm.eknmlogistics.mapInteraction

import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import io.reactivex.Single
import javax.inject.Inject

class MapPaddingManager @Inject constructor(
    private val lazyMap: Single<GoogleMap>
) {
    private var paddingTop = 0
    private var paddingBottom = 0
    private var paddingStart = 0
    private var paddingEnd = 0

    fun setPaddingBottom(paddingBottom: Int) {
        this.paddingBottom = paddingBottom
        updatePadding()
    }

    @SuppressLint("CheckResult")
    fun updatePadding() {
        lazyMap
            .subscribe { map ->
                map.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
            }
    }

}