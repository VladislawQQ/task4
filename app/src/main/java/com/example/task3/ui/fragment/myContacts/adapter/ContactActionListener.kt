package com.example.task3.ui.fragment.myContacts.adapter

import android.view.View
import com.example.task3.data.contacts.model.Contact

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactClick(contact: Contact, transitionNames: Array<Pair<View, String>>)
}