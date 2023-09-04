package com.example.messengerApp.ui.authentication.auth

import androidx.lifecycle.ViewModel
import com.example.messengerApp.ui.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
) : ViewModel() {

    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
    fun emailIsValid(email: String): Boolean = Validation.emailIsValid(email)

    /**
     * Check if password is valid with regex
     * if !isValid return String
     * else return null
     */
    fun passwordIsValid(password: String): Validation.CODES? = Validation.passwordIsValid(password)

}