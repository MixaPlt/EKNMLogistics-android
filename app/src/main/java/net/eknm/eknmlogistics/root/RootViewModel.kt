package net.eknm.eknmlogistics.root

import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : BaseFlowViewModel() {

    private val _onLoggedOutEvent = SingleLiveEvent<Unit>()
    val onLoggedOutEvent: LiveData<Unit> = _onLoggedOutEvent

    fun logOut() {
        authorizationRepository.logOut()
    }

    override fun onInitialize() {
        executeDisposable {
            authorizationRepository
                .trackSession()
                .subscribe {
                    if (it.item == null) {
                        _onLoggedOutEvent.call()
                    }
                }
        }
    }
}