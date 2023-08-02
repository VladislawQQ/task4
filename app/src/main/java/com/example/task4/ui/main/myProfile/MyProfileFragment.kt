package com.example.task4.ui.main.myProfile

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.task4.databinding.FragmentMyProfileBinding
import com.example.task4.base.BaseFragment
import com.example.task4.ui.main.viewpager.ViewPagerFragment
import com.example.task4.constants.Constants.REGEX_EMAIL_PARSE
import com.example.task4.ui.utils.ext.setContactPhoto
import java.util.Locale

class MyProfileFragment
    : BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val args : MyProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askPermission()
        setListeners()
        roundProfilePhoto()
        setNameByEmail(args.email)
    }

    private fun roundProfilePhoto() {
        with(binding) {
            fragmentMyProfileImageViewProfilePhoto.setContactPhoto(
                fragmentMyProfileImageViewProfilePhoto.drawable
            )
        }
    }

    private fun setNameByEmail(email: String): String {
        val parsedName = parseEmail(email)

        return if (parsedName.size > 1) {
            val name = parsedName.first().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            val surname = parsedName[1].replaceFirstChar { it.titlecase(Locale.getDefault()) }

            "$name $surname"
        } else {
            parsedName.first()
        }
    }

    private fun setListeners() {
        binding.fragmentMyProfileButtonViewContacts.setOnClickListener { viewMyContactsButton() }
        binding.fragmentMyProfileTextViewLogout.setOnClickListener { logout() }
    }

    private fun logout() {
        navController.popBackStack()
    }

    private fun viewMyContactsButton() {
        (parentFragment as ViewPagerFragment).openTab(1)
    }

    private fun parseEmail(email: String): List<String> {
        return REGEX_EMAIL_PARSE.toRegex().replace(email, "").split(".")
    }


    private fun askPermission() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)

        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
        } else {
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                1)
        }
    }

    companion object {
        private var READ_CONTACTS_GRANTED = false
    }

}