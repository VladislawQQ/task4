package com.example.task3.ui.fragment.addContact

import com.example.task3.data.contacts.model.Contact

interface ConfirmationListener {
    fun onConfirmButtonClicked(contact: Contact)
}