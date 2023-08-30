package com.example.task4.ui.main.viewpager.myContacts.addContact

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.task4.ui.utils.Constants.DEFAULT_NAME
import com.example.task4.data.models.Contact
import com.example.task4.databinding.DialogAddContactBinding
import com.example.task4.ui.utils.ext.setContactPhoto

class AddContactDialogFragment : DialogFragment() {

    private lateinit var listener: ConfirmationListener
    private lateinit var _binding: DialogAddContactBinding

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
                Contact ( // TODO: set default values
                    name = dialogAddContactEditTextUsername.text.toString().ifBlank { DEFAULT_NAME },
                    career = dialogAddContactEditTextCareer.text.toString(),
                    photo = "",
                    email = "",
                    phone = "",
                    address = "",
                    dateOfBirthday = ""
                )
            }
        )
        dismiss()
    }

    private fun imageViewBackListener() {
        dismiss()
    }

    companion object {
        const val TAG_ADD_CONTACT = "Add contact dialog"
    }
}