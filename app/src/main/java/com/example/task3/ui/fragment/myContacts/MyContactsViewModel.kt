package com.example.task3.ui.fragment.myContacts

import androidx.lifecycle.ViewModel
import com.example.task3.data.contacts.ContactService
import com.example.task3.data.contacts.model.Contact
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    private val contactService = ContactService()
    
    val contacts : StateFlow<List<Contact>> = contactService.contacts

    fun getContact(index: Int): Contact = contactService.getContactByIndex(index)

    fun getContactIndex(contact: Contact) : Int = contactService.getContactIndex(contact)

    fun deleteContact(contact: Contact): Int = contactService.deleteContact(contact)

    fun addContact(index: Int, contact: Contact) {
        contactService.addContact(index, contact)
    }

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
}