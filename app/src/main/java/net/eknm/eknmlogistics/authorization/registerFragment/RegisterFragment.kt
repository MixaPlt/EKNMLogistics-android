package net.eknm.eknmlogistics.authorization.registerFragment

import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {
    override val layoutResId = R.layout.fragment_register
    override val vmClass = RegisterViewModel::class.java

    companion object {
        fun newInstance() = RegisterFragment()
    }
}