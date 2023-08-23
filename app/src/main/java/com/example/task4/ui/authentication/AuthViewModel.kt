package com.example.task4.ui.authentication

import androidx.lifecycle.ViewModel
import com.example.task4.constants.Validation

class AuthViewModel : ViewModel() {

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
    fun passwordIsValid(password: String): Int? = Validation.passwordIsValid(password)

}