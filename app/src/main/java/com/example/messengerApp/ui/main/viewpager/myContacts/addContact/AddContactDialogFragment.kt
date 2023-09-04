package com.example.messengerApp.ui.main.viewpager.myContacts.addContact

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.messengerApp.databinding.DialogAddContactBinding
import com.example.messengerApp.ui.utils.Constants.DEFAULT_NAME
import com.example.messengerApp.ui.utils.ext.setContactPhoto
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddContactDialogFragment : DialogFragment() {

    private val viewModel : AddContactViewModel by viewModels()

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        setListeners()

        binding.dialogAddContactImageViewProfilePhoto.setContactPhoto()
        builder.setView(binding.root)
        return builder.create()
    }

    private fun setListeners() {
        with(binding) {
            dialogAddContactImageViewBack.setOnClickListener { imageViewBackListener() }
            dialogAddContactButtonSave.setOnClickListener { saveButtonListener() }
        }
    }

    private fun saveButtonListener() {
        with(binding) {
            viewModel.saveNewContact(
                name = dialogAddContactEditTextUsername.text.toString().ifBlank { DEFAULT_NAME },
                career = dialogAddContactEditTextCareer.text.toString(),
                photo = dialogAddContactImageViewProfilePhoto.toString(),
                email = dialogAddContactEditTextEmail.toString(),
                phone = dialogAddContactEditTextPhone.toString(),
                address = dialogAddContactEditTextAddress.toString(),
                dateOfBirthday = dialogAddContactEditTextDateOfBirth.toString()
            )
        }
        dismiss()
    }

    private fun imageViewBackListener() {
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG_ADD_CONTACT : String = AddContactDialogFragment::class.java.name
    }
}