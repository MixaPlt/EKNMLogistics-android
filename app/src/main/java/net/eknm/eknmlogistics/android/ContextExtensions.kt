package net.eknm.eknmlogistics.android

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import net.eknm.eknmlogistics.R

fun Context.showStubToast() {
    showToast(R.string.toast_coming_soon)
}

fun Context.showToast(@StringRes textRes: Int) {
    val text = getString(textRes)
    showToast(text)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, resources.getInteger(R.integer.toast_duration)).show()
}

fun Fragment.showStubToast() {
    context!!.showStubToast()
}