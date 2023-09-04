package com.example.messengerApp.ui.main.viewpager.myContacts.adapter

import android.view.View
import com.example.messengerApp.ui.main.viewpager.myContacts.model.ContactListItem

interface ContactActionListener {
    fun onContactDelete(contact: ContactListItem)
    fun onContactClick(contact: ContactListItem, transitionNames: Array<Pair<View, String>>)
    fun onContactLongClick(contact: ContactListItem)
}