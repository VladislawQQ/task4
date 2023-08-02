package com.example.task4.ui.main.myContacts.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.task4.data.models.Contact

class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem.id == newItem.id
}