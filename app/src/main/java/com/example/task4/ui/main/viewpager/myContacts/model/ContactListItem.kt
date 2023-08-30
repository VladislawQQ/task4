package com.example.task4.ui.main.viewpager.myContacts.model

import com.example.task4.data.models.Contact

data class ContactListItem(
    val contact : Contact,
    val isChecked : Boolean,
    var isMultiselect: Boolean
) {
    val id: Long = contact.id
    val photo: String? = contact.photo
    val name: String = contact.name
    val career: String = contact.career
    val email: String = contact.email
    val phone: String = contact.phone
    val address: String = contact.address
    val dateOfBirthday: String = contact.dateOfBirthday
}