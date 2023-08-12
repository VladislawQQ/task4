package com.example.task4.ui.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.example.task4.R
import com.example.task4.base.BaseFragment
import com.example.task4.constants.Constants.PASSWORD_LENGTH
import com.example.task4.constants.Constants.REGEX_DIGITS
import com.example.task4.constants.Constants.REGEX_UPPER_CASE
import com.example.task4.databinding.FragmentAuthBinding

class AuthenticationFragment
    : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

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

    private fun registerButton() {
        if (!fieldsIsEmpty()) {
            startNextFragment()
        }
    }

    private fun startNextFragment() {
        val email = binding.fragmentAuthEditTextEmail.text.toString()

        val direction = AuthenticationFragmentDirections.startMainActivity(email)

        navController.navigate(direction)
    }

    private fun emailErrorChanges() {
        with(binding) {
            fragmentAuthEditTextEmail.doAfterTextChanged {
                fragmentAuthContainerEmail.error = emailIsValid()
            }
        }
    }

    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
    private fun emailIsValid(): String? {
        val email = binding.fragmentAuthEditTextEmail.text.toString()
        val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isValid)
            return getString(R.string.invalid_email_address)

        return null
    }

    private fun passwordErrorChanges() {
        with(binding) {
            fragmentAuthEditTextPassword.doAfterTextChanged {
                fragmentAuthContainerPassword.error = passwordIsValid()
            }
        }
    }

    /**
     * Check if password is valid with regex
     * if !isValid return String
     * else return null
     */
    private fun passwordIsValid(): String? {
        val passwordText = binding.fragmentAuthEditTextPassword.text.toString()

        return if (passwordText.contains(" ")) {
            getString(R.string.dont_use_spaces)
        } else if (passwordText.length < PASSWORD_LENGTH) {
            getString(R.string.min_8_chars_pass)
        } else if (!REGEX_UPPER_CASE.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_upper_case_chars)
        } else if (!REGEX_DIGITS.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_number_chars)
        } else null
    }

    private fun fieldsIsEmpty(): Boolean {
        with(binding) {
            val validEmail = fragmentAuthContainerEmail.error == null
            val validPassword = fragmentAuthContainerPassword.error == null
            val emailIsEmpty = fragmentAuthEditTextEmail.text.toString().isEmpty()
            val passwordIsEmpty = fragmentAuthEditTextPassword.text.toString().isEmpty()

            if (validEmail and validPassword and emailIsEmpty and passwordIsEmpty)
                return false

            return true
        }
    }
}