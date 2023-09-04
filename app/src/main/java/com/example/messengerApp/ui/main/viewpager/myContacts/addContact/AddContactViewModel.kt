package com.example.messengerApp.ui.main.viewpager.myContacts.addContact

import androidx.lifecycle.ViewModel
import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.data.repository.interfaces.ContactService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    contactService: ContactService
) : ViewModel() {

    private val _contactService = contactService

    fun saveNewContact(
        name: String,
        photo: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        dateOfBirthday: String,
    ) {
        _contactService.addContact(
            Contact(
                name = name,
                photo = photo,
                career = career,
                email = email,
                phone = phone,
                address = address,
                dateOfBirthday = dateOfBirthday
            )
        )
    }

}