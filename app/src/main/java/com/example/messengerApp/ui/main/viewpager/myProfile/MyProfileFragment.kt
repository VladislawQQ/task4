package com.example.messengerApp.ui.main.viewpager.myProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.messengerApp.base.BaseFragment
import com.example.messengerApp.databinding.FragmentMyProfileBinding
import com.example.messengerApp.ui.authentication.AuthActivity
import com.example.messengerApp.ui.main.viewpager.ViewPagerFragment
import com.example.messengerApp.ui.utils.Constants
import com.example.messengerApp.ui.utils.ext.setContactPhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment
    : BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val viewModel : MyProfileViewModel by viewModels()

    private val args : MyProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        roundProfilePhoto()

        setUserName(viewModel.setNameByEmail(args.email))
    }

    private fun roundProfilePhoto() {
        with(binding) {
            fragmentMyProfileImageViewProfilePhoto.setContactPhoto(
                fragmentMyProfileImageViewProfilePhoto.drawable
            )
        }
    }

    private fun setListeners() {
        binding.fragmentMyProfileButtonViewContacts.setOnClickListener { viewMyContactsButton() }
        binding.fragmentMyProfileTextViewLogout.setOnClickListener { logout() }
        backPressedListener()
    }

    private fun logout() {
        val activity = requireActivity()
        val intentObject = Intent(activity, AuthActivity::class.java)
        startActivity(intentObject)
        activity.finish()
    }

    private fun backPressedListener() {
        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                logout()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }

    private fun viewMyContactsButton() {
        (parentFragment as ViewPagerFragment).openTab(Constants.SCREENS.CONTACTS_SCREEN.ordinal)
    }

    private fun setUserName(nameByEmail: String) {
        binding.fragmentMyProfileTextViewProfileName.text = nameByEmail
    }
}
