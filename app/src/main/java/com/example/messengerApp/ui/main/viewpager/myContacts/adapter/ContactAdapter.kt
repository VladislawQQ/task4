package com.example.messengerApp.ui.main.viewpager.myContacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerApp.R
import com.example.messengerApp.ui.utils.Constants.TRANSITION_NAME_CAREER
import com.example.messengerApp.ui.utils.Constants.TRANSITION_NAME_CONTACT_NAME
import com.example.messengerApp.ui.utils.Constants.TRANSITION_NAME_IMAGE
import com.example.messengerApp.databinding.ContactItemBinding
import com.example.messengerApp.ui.main.viewpager.myContacts.model.ContactListItem
import com.example.messengerApp.ui.main.viewpager.myContacts.adapter.diffUtil.ContactDiffUtil
import com.example.messengerApp.ui.utils.ext.setContactPhoto

class ContactAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<ContactListItem, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

    var isMultiSelectMode = false
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
        fun bind(contact: ContactListItem) {
            with(binding) {
                contactItemTextViewName.text = contact.name
                contactItemTextViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

                contactItemImageViewProfilePhoto.setContactPhoto(contact.photo)
            }
            setListeners(contact)
        }

        private fun setListeners(contact: ContactListItem) {
            if (isMultiSelectMode) {
                setSelectList(contact)
            }
            else
                binding.contactItemImageViewBucket.setOnClickListener {
                    contactActionListener.onContactDelete(contact)
                }

            itemView.setOnClickListener { onItemClick(contact) }

            itemView.setOnLongClickListener {
                contactActionListener.onContactLongClick(contact)
                true
            }

        }

        private fun setSelectList(contact: ContactListItem) {
            with(binding) {
                contactItemCheckboxSelectMode.visibility = if (isMultiSelectMode) VISIBLE else GONE
                contactItemCheckboxSelectMode.isChecked = contact.isChecked
                contactItemImageViewBucket.visibility = if (isMultiSelectMode) GONE else VISIBLE
                llItemView.background = if (isMultiSelectMode)
                    ContextCompat.getDrawable(
                        root.context,
                        R.drawable.border_contact_item_multiselect
                    )
                else
                    ContextCompat.getDrawable(root.context, R.drawable.border_contact_item)
            }
        }

        private fun onItemClick(contact: ContactListItem) {
            with(binding) {
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

                if (isMultiSelectMode) {
                    contactItemCheckboxSelectMode.isChecked =
                        contact.isChecked
                }
            }
        }

        private fun setTransitionName(view: View, transitionName: String): Pair<View, String> {
            view.transitionName = transitionName
            return view to transitionName
        }
    }
}