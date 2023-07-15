package com.example.task3.ui.fragment.myContacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.ui.adapter.diffUtil.ContactDiffUtil
import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.ContactItemBinding
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_CAREER
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_CONTACT_NAME
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_IMAGE
import com.example.task3.ui.utils.ext.setContactPhoto

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

                setListeners(contact)

                with(contactItemImageViewProfilePhoto) {
                    if (contact.photo.isNotBlank())
                        setContactPhoto(contact.photo)
                    else setContactPhoto()
                }
            }
        }

        private fun setListeners(contact: Contact) {
            with(binding) {
                contactItemImageViewBucket.setOnClickListener {
                    contactActionListener.onContactDelete(
                        contact
                    )
                }

                itemView.setOnClickListener {
                    contactActionListener.onContactClick(
                        contact,
                        arrayOf(
                            setTransitionName(
                                contactItemImageViewProfilePhoto,
                                TRANSITION_NAME_IMAGE
                            ),
                            setTransitionName(
                                contactItemTextViewName,
                                TRANSITION_NAME_CONTACT_NAME
                            ),
                            setTransitionName(contactItemTextViewCareer, TRANSITION_NAME_CAREER)
                        )
                    )
                }
            }
        }

        private fun setTransitionName (view: View, transitionName : String): Pair<View, String> {
            view.transitionName = transitionName
            return view to transitionName
        }
    }
}