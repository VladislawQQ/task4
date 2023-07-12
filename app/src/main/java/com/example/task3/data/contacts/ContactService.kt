package com.example.task3.data.contacts

import com.example.task3.data.contacts.model.Contact
import com.example.task3.ui.utils.ext.logExt
import kotlinx.coroutines.flow.MutableStateFlow

class ContactService {

    var contacts = MutableStateFlow<List<Contact>>(emptyList())
    private val contactProvider = ContactGenerator()

    init {
        if (contacts.value.isEmpty()) {
            val contactsPhone = MutableStateFlow<List<Contact>>(emptyList())
            try {
                contactsPhone.value = contactProvider.getPhoneContacts()
            } catch (e: Exception) {
                logExt("Catch! ${e.message}")
            }

            contacts =
                if (contactsPhone.value.isNotEmpty()) contactsPhone
                    else
                        contactProvider.generateContacts()
        }
    }

    fun deleteContact(contact: Contact): Int {
        val indexToDelete : Int

        contacts.value = contacts.value.toMutableList().apply {
            indexToDelete  = indexOf(contact)
            remove(contact)
        }

        return indexToDelete
    }

    fun addContact(contact: Contact) {
        contacts.value = contacts.value.toMutableList().apply {
            add(contact)
        }
    }

    fun addContact(index: Int, contact: Contact) {
        contacts.value = contacts.value.toMutableList().apply {
            add(index, contact)
        }
    }

    fun getContactIndex(contact: Contact) : Int = contacts.value.indexOf(contact)

    fun getContactByIndex(index: Int): Contact {
        return contacts.value[index]
    }
}