package net.eknm.eknmlogistics.android.base.navigation

import android.os.Bundle
import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.SingleLiveEvent

abstract class BaseFragmentViewModel : BaseViewModel(), DrawerManager by DrawerManagerImpl() {
    private val _onNavigateBack = SingleLiveEvent<Unit>()
    val onNavigateBack: LiveData<Unit> = _onNavigateBack

    private val _showToastEvent = SingleLiveEvent<Int>() // String res
    val showToastEvent: LiveData<Int> = _showToastEvent

    private val _showStringToastEvent = SingleLiveEvent<String>()
    val showStringToastEvent: LiveData<String> = _showStringToastEvent

    fun showToast(textRes: Int) {
        _showToastEvent.value = textRes
    }

    fun showToast(text: String) {
        _showStringToastEvent.value = text
    }

    fun showStubToast() {
        showToast(R.string.toast_coming_soon)
    }

    fun onSaveInstanceState(outState: Bundle) {

    }

    fun onViewStateRestored(savedInstanceState: Bundle) {

    }

    fun handleBackPress() {
        goBack()
    }

    fun goBack() {
        _onNavigateBack.call()
    }
}