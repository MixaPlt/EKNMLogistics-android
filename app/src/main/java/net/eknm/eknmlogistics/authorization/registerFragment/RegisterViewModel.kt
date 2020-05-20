package net.eknm.eknmlogistics.authorization.registerFragment

import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentViewModel
import net.eknm.eknmlogistics.android.ioToMain
import net.eknm.eknmlogistics.authorization.authorizationRepository.AuthorizationRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : BaseFragmentViewModel() {
    fun registerUser(email: String, name: String, password: String) {
        executeDisposable {
            authorizationRepository
                .registerUser(email, name, password)
                .ioToMain()
                .subscribe({
                    showToast("User successfully created")
                }, {
                    showToast(it.message ?: "")
                })
        }
    }
}