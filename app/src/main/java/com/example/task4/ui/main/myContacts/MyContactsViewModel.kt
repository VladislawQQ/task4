package com.example.task4.ui.main.myContacts

import androidx.lifecycle.ViewModel
import com.example.task4.data.contacts.ContactService
import com.example.task4.data.models.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    private val contactService = ContactService()

    val contacts : StateFlow<List<Contact>> = contactService.contacts

    private val _isMultiSelectMode = MutableStateFlow(false)

    val isMultiSelectMode = _isMultiSelectMode

    fun getContact(index: Int): Contact = contactService.getContactByIndex(index)

    fun getContactIndex(contact: Contact) : Int = contactService.getContactIndex(contact)

    fun deleteContact(contact: Contact): Int = contactService.deleteContact(contact)

    fun addContact(index: Int, contact: Contact) {
        contactService.addContact(index, contact)
    }

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
    fun changeMultiSelectMode() {
        _isMultiSelectMode.value = !_isMultiSelectMode.value
    }
    fun clearSelectedItems() {
        contactService.clearSelectedItems()
    }
    fun toggle(contact: Contact) {
        contactService.toggle(contact)
    }
    fun sizeOfSelectedItems() = contactService.sizeOfSelectedItems()
}