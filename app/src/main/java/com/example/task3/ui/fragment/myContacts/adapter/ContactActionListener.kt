package com.example.task3.ui.fragment.myContacts.adapter

import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.ContactItemBinding

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactClick(bindingContactPhoto: ContactItemBinding, contact: Contact)
}