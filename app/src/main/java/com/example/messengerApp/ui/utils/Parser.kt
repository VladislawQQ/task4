package com.example.messengerApp.ui.utils

object Parser {

    fun parseEmail(email: String): List<String> =
        Validation.REGEX_EMAIL_PARSE.toRegex().replace(email, "").split(".")

}