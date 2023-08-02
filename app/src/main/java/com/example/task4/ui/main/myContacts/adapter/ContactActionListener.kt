package com.example.task4.ui.main.myContacts.adapter

import android.view.View
import com.example.task4.data.models.Contact

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactClick(contact: Contact, transitionNames: Array<Pair<View, String>>)
    fun onContactLongClick(contact: Contact)
}