package net.eknm.eknmlogistics.root

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import net.eknm.eknmlogistics.authorization.authorizationRepository.User
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : BaseFlowViewModel() {

    val userName = ObservableField<String>("you")

    private val _onLoggedOutEvent = SingleLiveEvent<Unit>()
    val onLoggedOutEvent: LiveData<Unit> = _onLoggedOutEvent

    fun logOut() {
        authorizationRepository.logOut()
    }

    private fun onUserChanged(newUser: User) {
        userName.set(newUser.name)
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

        executeDisposable {
            authorizationRepository
                .trackUser()
                .subscribe { optionalUser ->
                    optionalUser.item?.let {
                        onUserChanged(it)
                    }
                }
        }
    }
}