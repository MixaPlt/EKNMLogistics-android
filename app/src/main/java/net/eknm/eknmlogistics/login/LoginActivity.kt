package net.eknm.eknmlogistics.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragmentActivity
import net.eknm.eknmlogistics.login.loginFragment.LoginFragment
import net.eknm.eknmlogistics.login.loginRegisterFragment.LoginRegisterFragment

class LoginActivity : BaseFragmentActivity<LoginActivityViewModel>() {
    override val layoutId = R.layout.layout_flow
    override val vmClass = LoginActivityViewModel::class.java
    private val currentFragment get() = supportFragmentManager.findFragmentById(R.id.container)
    override fun init() {
        showFragment(LoginRegisterFragment.newInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is LoginRegisterFragment -> observeLoginRegisterFragment(fragment)
        }
    }

    private fun observeLoginRegisterFragment(fragment: LoginRegisterFragment) {
        fragment.viewModel.onLoginClickEvent.observe(this, Observer {
            showFragment(LoginFragment.newInstance())
        })
    }

    private fun showFragment(
        fragment: Fragment,
        shouldAddToBackStack: Boolean = currentFragment != null
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .apply {
                if (shouldAddToBackStack) {
                    addToBackStack(null)
                }
            }
            .commit()
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            finish()
        }
    }
}