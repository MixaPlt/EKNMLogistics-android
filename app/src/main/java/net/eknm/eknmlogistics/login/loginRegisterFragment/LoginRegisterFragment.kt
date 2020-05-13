package net.eknm.eknmlogistics.login.loginRegisterFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentLoginOrRegisterBinding

class LoginRegisterFragment :
    BaseFragment<LoginRegisterViewModel, FragmentLoginOrRegisterBinding>() {
    override val layoutResId = R.layout.fragment_login_or_register
    override val vmClass = LoginRegisterViewModel::class.java

    companion object {
        fun newInstance() = LoginRegisterFragment()
    }
}