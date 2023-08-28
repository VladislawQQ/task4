package com.example.task4.ui.main.viewpager.myContacts.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.task4.ui.main.viewpager.myContacts.model.ContactListItem

class ContactDiffUtil : DiffUtil.ItemCallback<ContactListItem>() {

    override fun areItemsTheSame(oldItem: ContactListItem, newItem: ContactListItem):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: ContactListItem, newItem: ContactListItem):
            Boolean = oldItem.id == newItem.id
}