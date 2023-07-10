package com.example.task3.ui.fragment.myContacts.adapter

import com.example.task3.data.contacts.model.Contact

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactClick(contact: Contact)
}