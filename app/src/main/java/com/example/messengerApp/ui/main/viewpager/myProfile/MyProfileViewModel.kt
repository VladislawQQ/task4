package com.example.messengerApp.ui.main.viewpager.myProfile

import androidx.lifecycle.ViewModel
import com.example.messengerApp.ui.utils.Constants
import com.example.messengerApp.ui.utils.Parser
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
) : ViewModel() {

    fun setNameByEmail(email: String): String {
        val parsedName = Parser.parseEmail(email)

        return if (parsedName.size > 1) {
            val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }

            "$name $surname"
        } else {
            parsedName.first().ifEmpty { Constants.DEFAULT_NAME }
        }
    }
}