package net.eknm.eknmlogistics.authorization.loginFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override val layoutResId = R.layout.fragment_login
    override val vmClass = LoginViewModel::class.java

    companion object {
        fun newInstance() = LoginFragment()
    }
}