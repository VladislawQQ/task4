package com.example.task3.ui.fragment.addContact

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.task3.data.contacts.ContactGenerator
import com.example.task3.databinding.DialogAddContactBinding
import com.example.task3.ui.utils.ext.setContactPhoto

class AddContactDialogFragment : DialogFragment() {

    private lateinit var listener: ConfirmationListener
    private lateinit var _binding: DialogAddContactBinding
    private val _contactGenerator = ContactGenerator()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        setListeners()

        _binding.dialogAddContactImageViewProfilePhoto.setContactPhoto()
        builder.setView(_binding.root)
        return builder.create()
    }

    private fun setListeners() {
        _binding.dialogAddContactImageViewBack.setOnClickListener { imageViewBackListener() }
        _binding.dialogAddContactButtonSave.setOnClickListener { saveButtonListener() }

        try {
            listener = requireParentFragment() as ConfirmationListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context or ${requireParentFragment()} must implement ConfirmationListener")
        }
    }


    private fun saveButtonListener() {
        listener.onConfirmButtonClicked(
            with(_binding) {
                _contactGenerator.createContact(
                    userName = dialogAddContactEditTextUsername.text.toString(),
                    career = dialogAddContactEditTextCareer.text.toString()
                )
            }
        )
        dismiss()
    }

    private fun imageViewBackListener() {
        dismiss()
    }
}