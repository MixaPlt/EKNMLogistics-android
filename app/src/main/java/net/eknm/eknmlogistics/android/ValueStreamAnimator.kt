package net.eknm.eknmlogistics.android

import android.view.Choreographer
import kotlin.math.roundToLong

class ValueStreamAnimator<T>(
    initialValue: T,
    private val propertySetter: (T) -> Unit,
    private val interpolator: (currentValue: T, targetValue: T, timeMills: Long) -> T
) {
    private val choreographer = Choreographer.getInstance()
    var targetValue = initialValue
    private var currentValue = initialValue
    private var isStarted = false

    private fun postNextFrame() {
        choreographer.postFrameCallback { nanoTimeOnPreviousFrame ->
            onNextFrame(((System.nanoTime() - nanoTimeOnPreviousFrame) * 1e-6).roundToLong())
        }
    }

    fun start() {
        if (!isStarted) {
            isStarted = true
            postNextFrame()
        }
    }

    fun stop() {
        isStarted = false
    }

    private fun setValue(value: T) {
        propertySetter(value)
        currentValue = value
    }

    private fun onNextFrame(frameTimeMills: Long) {
        if (!isStarted) {
            return
        }
        postNextFrame()
        setValue(interpolator(currentValue, targetValue, frameTimeMills))
    }
}