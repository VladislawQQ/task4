package com.example.task3.ui.fragment.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task3.R
import com.example.task3.databinding.FragmentAuthBinding

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
        binding.fragmentAuthButtonRegister.setOnClickListener { registerButtonListenerAction() }
        binding.fragmentAuthButtonGoogle.setOnClickListener { googleButtonListenerAction() }
    }

    private fun googleButtonListenerAction() {
        val email = binding.fragmentAuthEditTextEmail.text.toString()

        val direction = AuthenticationFragmentDirections
            .actionAuthenticationFragmentToMyProfileFragment(email)

        findNavController().navigate(direction)
    }

    private fun registerButtonListenerAction() {
        with(binding) {
            if (!fieldsIsEmpty() &&
                fragmentAuthEditTextEmail.text.toString() != "" &&
                fragmentAuthEditTextPassword.text.toString() != ""
            ) {
                val email = fragmentAuthEditTextEmail.text.toString()

                val direction = AuthenticationFragmentDirections
                    .actionAuthenticationFragmentToMyProfileFragment(email)

                findNavController().navigate(direction)
            }
        }
    }

    private fun emailErrorChanges() {
        binding.fragmentAuthEditTextEmail.doAfterTextChanged {
            binding.fragmentAuthContainerEmail.error = emailIsValid()
        }
    }

    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
    private fun emailIsValid(): String? {
        val emailText = binding.fragmentAuthEditTextEmail.text.toString()
        val isValid = Patterns.EMAIL_ADDRESS.matcher(emailText).matches()

        if (!isValid)
            return getString(R.string.invalid_email_address)

        return null
    }

    private fun passwordErrorChanges() {
        binding.fragmentAuthEditTextPassword.doAfterTextChanged {
            binding.fragmentAuthContainerPassword.error = passwordIsValid()
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
        } else if (passwordText.length < 8) {
            getString(R.string.min_8_chars_pass)
        } else if (!REGEX_UPPER_CASE.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_upper_case_chars)
        } else if (!REGEX_DIGITS.toRegex().containsMatchIn(passwordText)) {
            getString(R.string.contain_number_chars)
        } else null
    }

    private fun fieldsIsEmpty(): Boolean {
        val validEmail = binding.fragmentAuthContainerEmail.error == null
        val validPassword = binding.fragmentAuthContainerPassword.error == null

        if (validEmail && validPassword)
            return false

        return true
    }

    companion object {
        private const val REGEX_UPPER_CASE = "[A-Z]"
        private const val REGEX_DIGITS = "[0-9]"
    }

}