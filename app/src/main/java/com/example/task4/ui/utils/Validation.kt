package com.example.task4.ui.utils // TODO: from constants package

import android.util.Patterns

object Validation {

    // Validation
    private const val REGEX_UPPER_CASE = "[A-Z]"
    private const val REGEX_DIGITS = "[0-9]"

    // Parse email
    const val REGEX_EMAIL_PARSE = "@.*\$"

    // Error codes for password validation
    const val CODE_SPACES = 0
    const val CODE_LENGTH = 1
    const val CODE_UPPER_CASE = 2
    const val CODE_DIGITS = 3

    // validate email
    fun emailIsValid(email : String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // validate password
    fun passwordIsValid(password: String): Int? {
        return if (password.contains(" ")) {
            CODE_SPACES
        } else if (password.length < Constants.PASSWORD_LENGTH) {
            CODE_LENGTH
        } else if (!REGEX_UPPER_CASE.toRegex().containsMatchIn(password)) {
            CODE_UPPER_CASE
        } else if (!REGEX_DIGITS.toRegex().containsMatchIn(password)) {
            CODE_DIGITS
        } else null
    }

}