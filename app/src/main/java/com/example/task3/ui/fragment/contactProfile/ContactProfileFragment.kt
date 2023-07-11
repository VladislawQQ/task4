package com.example.task3.ui.fragment.contactProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task3.R
import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.FragmentContactProfileBinding
import com.example.task3.ui.utils.ext.setContactPhoto

class ContactProfileFragment : Fragment(R.layout.fragment_contact_profile){

    private lateinit var binding : FragmentContactProfileBinding
    private val args : ContactProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactProfileBinding.bind(view)

        val contact : Contact = args.contact

        bindContactInfo(contact)
        setListeners()
    }

    private fun setListeners() {
        binding.fragmentMyProfileTextViewBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun bindContactInfo(contact: Contact) {
        with(binding) {
            fragmentMyProfileTextViewProfileName.text = contact.name
            fragmentMyProfileTextViewCareer.text = contact.career
            fragmentMyProfileImageViewProfilePhoto.setContactPhoto(contact.photo)
        }
    }


}