package com.example.task4.ui.main.viewpager.myProfile

import androidx.lifecycle.ViewModel
import com.example.task4.ui.utils.Parser
import java.util.Locale

class MyProfileViewModel(
) : ViewModel() {



    fun setNameByEmail(email: String): String { // TODO: to viewModel
        val parsedName = Parser.parseEmail(email)

        return if (parsedName.size > 1) {
            val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }

            "$name $surname"
        } else {
            parsedName.first()
        }
    }

}