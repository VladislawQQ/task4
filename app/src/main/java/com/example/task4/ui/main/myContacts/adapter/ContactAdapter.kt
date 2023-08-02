package com.example.task4.ui.main.myContacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task4.R
import com.example.task4.constants.Constants.TRANSITION_NAME_CAREER
import com.example.task4.constants.Constants.TRANSITION_NAME_CONTACT_NAME
import com.example.task4.constants.Constants.TRANSITION_NAME_IMAGE
import com.example.task4.data.models.Contact
import com.example.task4.databinding.ContactItemBinding
import com.example.task4.ui.main.myContacts.adapter.diffUtil.ContactDiffUtil
import com.example.task4.ui.utils.ext.setContactPhoto

class ContactAdapter(
    private val contactActionListener: ContactActionListener
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

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
        fun bind(contact: Contact) {
            with(binding) {
                contactItemTextViewName.text = contact.name
                contactItemTextViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

                with(contactItemImageViewProfilePhoto) {
                    if (contact.photo.isNotBlank())
                        setContactPhoto(contact.photo)
                    else setContactPhoto()
                }
            }
            setListeners(contact)
        }

        private fun setListeners(contact: Contact) {
            with(binding) {
                itemView.setOnClickListener { onItemClick(contact) }
                itemView.setOnLongClickListener {
                    contactActionListener.onContactLongClick(contact)
                    true
                }

                if (isMultiSelectMode) {
                    changeItemView(VISIBLE, GONE, R.drawable.border_contact_item_multiselect)
                } else {
                    changeItemView(GONE, VISIBLE, R.drawable.border_contact_item)
                    contactItemImageViewBucket.setOnClickListener {
                        contactActionListener.onContactDelete(
                            contact
                        )
                    }
                }
            }
        }
        private fun changeItemView(checkBoxVisibility: Int, bucketVisibility: Int, itemBackground: Int) {
            with(binding) {
                contactItemCheckboxSelectMode.visibility = checkBoxVisibility
                contactItemCheckboxSelectMode.isChecked = !contactItemCheckboxSelectMode.isChecked
                contactItemImageViewBucket.visibility = bucketVisibility
                llItemView.background = ContextCompat.getDrawable(
                    root.context,
                    itemBackground
                )
            }
        }

        private fun onItemClick(contact: Contact) {
            with(binding) {
                if (isMultiSelectMode) {
                    contactItemCheckboxSelectMode.isChecked =
                        !contactItemCheckboxSelectMode.isChecked
                } else {
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