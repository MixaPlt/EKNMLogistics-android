package net.eknm.eknmlogistics.authorization

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : BaseViewModel() {

    private val _changeFlowToRootEvent = SingleLiveEvent<Unit>()
    val changeFlowToRootEvent: LiveData<Unit> = _changeFlowToRootEvent

    private fun onLoggedIn() {
        _changeFlowToRootEvent.call()
    }

    override fun onInitialize() {
        executeDisposable {
            authorizationRepository
                .trackSession()
                .ioToMain()
                .subscribe {
                    if (it.item != null) {
                        onLoggedIn()
                    }
                }
        }
    }
}