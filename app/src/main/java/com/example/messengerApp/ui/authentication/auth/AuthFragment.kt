package com.example.messengerApp.ui.authentication.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.messengerApp.R
import com.example.messengerApp.base.BaseFragment
import com.example.messengerApp.databinding.FragmentAuthBinding
import com.example.messengerApp.ui.utils.Constants.PASSWORD_LENGTH
import com.example.messengerApp.ui.utils.Validation.CODES.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment
    : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    // TODO: remember me checkbox 

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailErrorChanges()
        passwordErrorChanges()

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            buttonRegister.setOnClickListener { registerButton() }
            buttonGoogle.setOnClickListener { googleButtonAction() }
        }
    }

    private fun googleButtonAction() {
        startNextFragment()
    }

    private fun registerButton() {
        with(binding) {
            if (viewModel.emailIsValid(editTextEmail.text.toString())
                && viewModel.passwordIsValid(editTextPassword.text.toString()) == null) {
                startNextFragment()
            } else {
                editTextEmail.text.toString().ifEmpty { containerEmail.error = getString(R.string.invalid_email_address) }
                editTextPassword.text.toString().ifEmpty { containerPassword.error = getString(R.string.invalid_password) }
            }
        }
    }

    private fun startNextFragment() {
        val email = binding.editTextEmail.text.toString()

        val direction = AuthFragmentDirections.startMainActivity(email)

        navController.navigate(direction)
    }

    private fun emailErrorChanges() {
        with(binding) {
            editTextEmail.doAfterTextChanged {
                val email = editTextEmail.text.toString()
                containerEmail.error =
                    if (!viewModel.emailIsValid(email))
                        getString(R.string.invalid_email_address)
                    else
                        null
            }
        }
    }

    private fun passwordErrorChanges() {
        with(binding) {
            editTextPassword.doAfterTextChanged {
                val password = binding.editTextPassword.text.toString()
                containerPassword.error =
                    when (viewModel.passwordIsValid(password)) {
                        CODE_SPACES -> getString(R.string.dont_use_spaces)
                        CODE_LENGTH -> getString(R.string.min_length_password, PASSWORD_LENGTH)
                        CODE_UPPER_CASE -> getString(R.string.contain_upper_case_chars)
                        CODE_DIGITS -> getString(R.string.contain_digit_chars)
                        else -> null
                    }
            }
        }
    }
}