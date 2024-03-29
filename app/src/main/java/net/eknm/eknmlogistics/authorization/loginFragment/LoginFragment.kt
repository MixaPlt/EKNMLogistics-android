package net.eknm.eknmlogistics.authorization.loginFragment

import android.os.Bundle
import android.view.View
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override val layoutResId = R.layout.fragment_login
    override val vmClass = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            viewModel.logIn(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}