package com.example.task3.ui.fragment.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task3.R
import com.example.task3.databinding.FragmentAuthBinding
import com.example.task3.ui.utils.Constants.PASSWORD_LENGTH
import com.example.task3.ui.utils.Constants.REGEX_DIGITS
import com.example.task3.ui.utils.Constants.REGEX_UPPER_CASE

class AuthenticationFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

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

        val direction = AuthenticationFragmentDirections
            .actionAuthenticationFragmentToMyProfileFragment(email)

        findNavController().navigate(direction)
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

            if (validEmail || validPassword || emailIsEmpty || passwordIsEmpty)
                return false

            return true
        }
    }
}