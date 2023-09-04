package com.example.messengerApp.data.repository

import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.data.repository.interfaces.ContactService
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.ContactMultiSelectHandler
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import com.example.messengerApp.ui.utils.ext.logExt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class ContactServiceImpl @Inject constructor(
) : ContactService, ContactMultiSelectHandler() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    override val contacts = _contacts.asStateFlow()

    private val contactProvider = ContactGeneratorImpl()
    private var lastDeleteContact: Contact? = null
    private var lastDeleteIndex: Int? = null

    init {
        setContacts()
    }

    override fun setContacts() {
        _contacts.value = contactProvider.generateContacts().value
        logExt(_contacts.value.size.toString())
    }

    override fun setPhoneContacts() {
        val contactsPhone = MutableStateFlow<List<Contact>>(emptyList())

        try {
            contactsPhone.value = contactProvider.getPhoneContacts()
        } catch (e: Exception) {
            logExt("Catch! ${e.message}")
        }

        logExt("Contacts : ${contactsPhone.value.size}")
        _contacts.value = contactsPhone.value
    }

    override fun deleteContact(index: Int) {
        if (index == -1) return

        lastDeleteIndex = index
        lastDeleteContact = _contacts.value[index]

        _contacts.value = _contacts.value.toMutableList().apply {
            remove(lastDeleteContact)
        }
    }

    override fun restoreContact() {
        _contacts.value = _contacts.value.toMutableList().apply {
            if (lastDeleteIndex != null && lastDeleteContact != null) {
                add(lastDeleteIndex!!, lastDeleteContact!!)
                lastDeleteIndex = null
                lastDeleteContact = null
            }
        }
    }

    override fun addContact(contact: Contact) {
        _contacts.value = _contacts.value.toMutableList().apply {
            add(contact)
        }
    }

    override fun deleteSelectedItems(multiChoiceState: MultiSelectState<Contact>) {
        _contacts.update { oldList ->
            oldList.filter { contact -> !multiChoiceState.isChecked(contact) }
        }
    }
}