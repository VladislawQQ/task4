package com.example.messengerApp.data.repository.interfaces

import com.example.messengerApp.data.models.Contact
import kotlinx.coroutines.flow.MutableStateFlow

interface ContactGenerator {

    fun generateContacts(): MutableStateFlow<List<Contact>>

    fun randomContact(): Contact

    fun getPhoneContacts(): MutableList<Contact>
}