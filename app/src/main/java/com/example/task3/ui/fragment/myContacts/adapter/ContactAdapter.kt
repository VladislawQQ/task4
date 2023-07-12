package com.example.task3.ui.fragment.myContacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.data.contacts.model.Contact
import com.example.task2.ui.adapter.diffUtil.ContactDiffUtil
import com.example.task3.ui.utils.ext.setContactPhoto
import com.example.task3.databinding.ContactItemBinding

class ContactAdapter(
    private val contactActionListener: ContactActionListener
)
    : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ContactViewHolder(
        private val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                contactItemTextViewName.text = contact.name
                contactItemTextViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

                contactItemImageViewBucket.setOnClickListener { contactActionListener.onContactDelete(contact) }
                itemView.setOnClickListener { contactActionListener.onContactClick(binding, contact) }

                with(contactItemImageViewProfilePhoto) {
                    if (contact.photo.isNotBlank())
                        setContactPhoto(contact.photo)
                    else setContactPhoto()
                }
            }
        }
    }
}