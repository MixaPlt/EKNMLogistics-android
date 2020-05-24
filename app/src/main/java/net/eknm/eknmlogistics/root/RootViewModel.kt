package net.eknm.eknmlogistics.root

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import net.eknm.eknmlogistics.android.base.SingleLiveEvent
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowViewModel
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import net.eknm.eknmlogistics.authorization.authorizationRepository.User
import net.eknm.eknmlogistics.mapInteraction.CenterLocationService
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val centerLocationService: CenterLocationService
) : BaseFlowViewModel() {

    val userName = ObservableField<String>("you")

    private val _onLoggedOutEvent = SingleLiveEvent<Unit>()
    val onLoggedOutEvent: LiveData<Unit> = _onLoggedOutEvent

    private val _flowType = SingleLiveEvent<FlowType>()
    val flowType: LiveData<FlowType> = _flowType
    private fun changeFlow(newFlowType: FlowType) {
        if (flowType.value != newFlowType) {
            _flowType.value = newFlowType
        }
    }

    fun initFlow() {
        showHome()
    }

    fun showHome() {
        changeFlow(FlowType.HOME)
    }

    fun showPayments() {
        changeFlow(FlowType.PAYMENTS)
    }

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

        centerLocationService.centerLocation()
    }
}