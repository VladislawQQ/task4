package com.example.messengerApp.ui.main.viewpager.myContacts.addContact

import com.example.messengerApp.data.models.Contact

interface ConfirmationListener {

    fun onConfirmButtonClicked(contact: Contact)
}