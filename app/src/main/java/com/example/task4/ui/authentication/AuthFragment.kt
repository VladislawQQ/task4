package com.example.task4.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.task4.R
import com.example.task4.base.BaseFragment
import com.example.task4.databinding.FragmentAuthBinding
import com.example.task4.ui.utils.Constants.PASSWORD_LENGTH
import com.example.task4.ui.utils.Validation.CODE_DIGITS
import com.example.task4.ui.utils.Validation.CODE_LENGTH
import com.example.task4.ui.utils.Validation.CODE_SPACES
import com.example.task4.ui.utils.Validation.CODE_UPPER_CASE

class AuthFragment
    : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Email and password validation
        emailErrorChanges()
        passwordErrorChanges()

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            fragmentAuthButtonRegister.setOnClickListener { registerButton() }
            fragmentAuthButtonGoogle.setOnClickListener { googleButtonAction() }
        }
    }

    private fun googleButtonAction() {
        startNextFragment()
    }

    private fun registerButton() { // TODO: checkbox?
        with(binding) {
            if (viewModel.emailIsValid(fragmentAuthEditTextEmail.text.toString()) && viewModel.passwordIsValid(
                    fragmentAuthEditTextPassword.text.toString()
                ) != null
            ) {
                startNextFragment()
            }
        }
    }

    private fun startNextFragment() {
        val email = binding.fragmentAuthEditTextEmail.text.toString()

        val direction = AuthFragmentDirections.startMainActivity(email)

        navController.navigate(direction)
    }

    private fun emailErrorChanges() {
        with(binding) {
            fragmentAuthEditTextEmail.doAfterTextChanged {
                val email = fragmentAuthEditTextEmail.text.toString()
                fragmentAuthContainerEmail.error =
                    if (viewModel.emailIsValid(email)) // TODO: confused
                        getString(R.string.invalid_email_address)
                    else
                        null
            }
        }
    }

    private fun passwordErrorChanges() {
        with(binding) {
            fragmentAuthEditTextPassword.doAfterTextChanged {
                val password = binding.fragmentAuthEditTextPassword.text.toString()
                fragmentAuthContainerPassword.error =
                    when (viewModel.passwordIsValid(password)) {// TODO: confused
                        CODE_SPACES -> getString(R.string.dont_use_spaces)
                        CODE_LENGTH -> getString(R.string.min_length_password, PASSWORD_LENGTH)
                        CODE_UPPER_CASE -> getString(R.string.contain_upper_case_chars)
                        CODE_DIGITS -> getString(R.string.contain_digit_chars)
                        else -> null
                    }
            }
        }
    }

    private fun fieldsIsEmpty(): Boolean { // TODO: can do more simple
        with(binding) {
            val validEmail = fragmentAuthContainerEmail.error == null
                    && fragmentAuthEditTextPassword.text.toString().isNotEmpty()

            val validPassword = fragmentAuthContainerPassword.error == null
                    && fragmentAuthEditTextPassword.text.toString().isNotEmpty()

            if (validEmail && validPassword)
                return false

            return true
        }
    }
}