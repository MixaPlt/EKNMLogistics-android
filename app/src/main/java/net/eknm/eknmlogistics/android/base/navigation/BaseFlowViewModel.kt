package net.eknm.eknmlogistics.android.base.navigation

import android.os.Bundle
import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent

abstract class BaseFlowViewModel : BaseViewModel(), DrawerManager by DrawerManagerImpl() {

    private val onFinishFlow = SingleLiveEvent<Void>()
    private val onCompleteFlow = SingleLiveEvent<Void>()

    open fun onSaveInstanceState(outState: Bundle) {

    }

    open fun onViewStateRestored(savedInstanceState: Bundle) {

    }

    fun getOnFinishFlowEvent(): LiveData<Void> = onFinishFlow
    fun getOnCompleteFlowEvent(): LiveData<Void> = onCompleteFlow

    fun finishFlow() {
        onFinishFlow.call()
    }

    fun completeFlow() {
        onCompleteFlow.call()
    }
}