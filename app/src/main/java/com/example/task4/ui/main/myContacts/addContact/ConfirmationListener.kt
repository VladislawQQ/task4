package com.example.task4.ui.main.myContacts.addContact

import com.example.task4.data.models.Contact

interface ConfirmationListener {
    fun onConfirmButtonClicked(contact: Contact)
}