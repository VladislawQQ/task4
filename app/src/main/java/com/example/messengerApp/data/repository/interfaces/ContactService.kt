package com.example.messengerApp.data.repository.interfaces

import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import kotlinx.coroutines.flow.StateFlow

interface ContactService {

    val contacts : StateFlow<List<Contact>>

    fun setContacts()

    fun setPhoneContacts()

    fun deleteContact(index: Int)

    fun restoreContact()

    fun addContact(contact: Contact)

    fun deleteSelectedItems(multiChoiceState: MultiSelectState<Contact>)

}