package com.example.task3.ui.main.myContacts.adapter

import android.view.View
import com.example.task3.data.contact_model.Contact

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactClick(contact: Contact, transitionNames: Array<Pair<View, String>>)
}