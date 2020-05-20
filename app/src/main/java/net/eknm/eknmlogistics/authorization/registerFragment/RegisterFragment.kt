package net.eknm.eknmlogistics.authorization.registerFragment

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register.*
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFragment
import net.eknm.eknmlogistics.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {
    override val layoutResId = R.layout.fragment_register
    override val vmClass = RegisterViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton.setOnClickListener {
            if (checkValidFields()) {
                viewModel.registerUser(
                    etEmail.text.toString(),
                    etLogin.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }

    private fun checkValidFields(): Boolean {
        when {
            !checkEmailValid() -> showToast(R.string.invalid_email_format)
            !checkNameValid() -> showToast(R.string.name_too_short)
            !checkPasswordValid() -> showToast(R.string.invalid_password)
            else -> return true
        }
        return false
    }

    private fun checkEmailValid(): Boolean {
        return etEmail.text?.contains('@') == true
    }

    private fun checkNameValid(): Boolean {
        return etLogin.text?.isNotBlank() == true
    }

    private fun checkPasswordValid(): Boolean {
        return etPassword.text?.let { it.length > 5 } == true
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}