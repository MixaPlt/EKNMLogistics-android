package net.eknm.eknmlogistics.authorization.loginRegisterFragment

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import javax.inject.Inject

class LoginRegisterViewModel @Inject constructor() : BaseFragmentViewModel() {
    private val _onRegisterClickEvent = SingleLiveEvent<Unit>()
    val onRegisterClickEvent: LiveData<Unit> = _onRegisterClickEvent

    private val _onLoginClickEvent = SingleLiveEvent<Unit>()
    val onLoginClickEvent: LiveData<Unit> = _onLoginClickEvent

    fun onRegisterClick() {
        _onRegisterClickEvent.call()
    }

    fun onLoginClick() {
        _onLoginClickEvent.call()
    }
}