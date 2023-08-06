package com.example.task4.ui.main.viewpager.myContacts

import com.example.task4.data.models.Contact

data class ContactListItem(
    val contact : Contact,
    val isChecked : Boolean
) {
    val id: Long = contact.id
    val photo: String = contact.photo
    val name: String = contact.name
    val career: String = contact.career
}