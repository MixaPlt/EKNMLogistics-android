package net.eknm.eknmlogistics.authorization.loginFragment

import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepositoryImpl
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepositoryImpl
): BaseFragmentViewModel() {
    fun logIn(email: String, password: String) {
        executeDisposable {
            authorizationRepository
                .logIn(email, password)
                .ioToMain()
                .subscribe({
                    showToast("Successfully logged in")
                }, {
                    showToast(it.message ?: "")
                })
        }
    }
}
