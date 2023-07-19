package com.example.task3.ui.main.myContacts.addContact

import com.example.task3.data.contact_model.Contact

interface ConfirmationListener {
    fun onConfirmButtonClicked(contact: Contact)
}