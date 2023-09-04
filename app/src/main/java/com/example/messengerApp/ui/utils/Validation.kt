package com.example.messengerApp.ui.utils

import android.util.Patterns

object Validation {

    // Validation
    private const val REGEX_UPPER_CASE = "[A-Z]"
    private const val REGEX_DIGITS = "[0-9]"

    // Parse email
    const val REGEX_EMAIL_PARSE = "@.*\$"



    // validate email
    fun emailIsValid(email : String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // validate password
    fun passwordIsValid(password: String): CODES? {
        return if (password.contains(" ")) {
            CODES.CODE_SPACES
        } else if (password.length < Constants.PASSWORD_LENGTH) {
            CODES.CODE_LENGTH
        } else if (!REGEX_UPPER_CASE.toRegex().containsMatchIn(password)) {
            CODES.CODE_UPPER_CASE
        } else if (!REGEX_DIGITS.toRegex().containsMatchIn(password)) {
            CODES.CODE_DIGITS
        } else null
    }

    // Error codes for password validation
    enum class CODES {
        CODE_SPACES,
        CODE_LENGTH,
        CODE_UPPER_CASE,
        CODE_DIGITS
    }
}