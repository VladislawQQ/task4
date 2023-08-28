package com.example.task4.data.contacts

import com.example.task4.data.models.Contact
import com.example.task4.ui.main.viewpager.myContacts.multiselect.ContactMultiSelectHandler
import com.example.task4.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import com.example.task4.ui.utils.ext.logExt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactService : ContactMultiSelectHandler() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()

    private val contactProvider = ContactGenerator()
    private var lastDeleteContact: Contact? = null
    private var lastDeleteIndex: Int? = null

    init {
        setContacts()
    }

    private fun setContacts() {
        val contactsPhone = MutableStateFlow<List<Contact>>(emptyList())
        try {
            contactsPhone.value = contactProvider.getPhoneContacts()
        } catch (e: Exception) {
            logExt("Catch! ${e.message}")
        }

        logExt("Contacts : ${contactsPhone.value.size}")
        _contacts.value =
            contactsPhone.value.ifEmpty {
                contactProvider.generateContacts().value
            }
    }
    fun setPhoneContacts() {
        val contactsPhone = MutableStateFlow<List<Contact>>(emptyList())

        try {
            contactsPhone.value = contactProvider.getPhoneContacts()
        } catch (e: Exception) {
            logExt("Catch! ${e.message}")
        }

        logExt("Contacts : ${contactsPhone.value.size}")
        _contacts.value = contactsPhone.value
    }

    fun deleteContact(index: Int) {
        if (index == -1) return

        lastDeleteIndex = index
        lastDeleteContact = _contacts.value[index]

        _contacts.value = _contacts.value.toMutableList().apply {
            remove(lastDeleteContact)
        }
    }

    fun restoreContact() {
        _contacts.value = _contacts.value.toMutableList().apply {
            if (lastDeleteIndex != null && lastDeleteContact != null) {
                add(lastDeleteIndex!!, lastDeleteContact!!)
                lastDeleteIndex = null
                lastDeleteContact = null
            }
        }
    }

    fun addContact(contact: Contact) {
        _contacts.value = _contacts.value.toMutableList().apply {
            add(contact)
        }
    }

    fun deleteSelectedItems(multiChoiceState: MultiSelectState<Contact>) {
        _contacts.update { oldList ->
            oldList.filter { contact -> !multiChoiceState.isChecked(contact) }
        }
    }
}