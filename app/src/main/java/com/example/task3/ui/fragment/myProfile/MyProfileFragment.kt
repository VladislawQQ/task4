package com.example.task3.ui.fragment.myProfile

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task3.R
import com.example.task3.databinding.FragmentMyProfileBinding
import com.example.task3.ui.utils.ext.setContactPhoto
import java.util.Locale

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private lateinit var binding : FragmentMyProfileBinding
    private val args : MyProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyProfileBinding.bind(view)

        askPermission()
        setListeners()
        roundProfilePhoto()

        val email = args.email
        setNameByEmail(email)
    }

    private fun roundProfilePhoto() {
        binding.fragmentMyProfileImageViewProfilePhoto.setContactPhoto(
            binding.fragmentMyProfileImageViewProfilePhoto.drawable
        )
    }

    private fun setNameByEmail(email: String) {
        if (email.isNotEmpty()) {
            val parsedName = parseEmail(email)

            val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }
            val profileName = "$name $surname"

            binding.fragmentMyProfileTextViewProfileName.text = profileName
        }
    }

    private fun setListeners() {
        binding.fragmentMyProfileButtonViewContacts.setOnClickListener { viewMyContactsButton() }
    }

    private fun viewMyContactsButton() {
        findNavController().navigate(
            R.id.action_myProfileFragment_to_myContactsFragment
        )
    }


    private fun parseEmail(email: String): List<String> {
        return REGEX_EMAIL_PARSE.toRegex().replace(email, "")
            .split(".")
    }


    private fun askPermission() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_CONTACTS)

        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
        } else {
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                1)
        }
    }

    companion object {
        private var READ_CONTACTS_GRANTED = false
        private const val REGEX_EMAIL_PARSE = "@.*\$"
    }

}