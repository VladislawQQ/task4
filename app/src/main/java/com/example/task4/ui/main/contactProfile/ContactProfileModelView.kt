package com.example.task4.ui.main.contactProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ContactProfileModelView(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = ContactProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)  // TODO: from viewModel

    val contact = args.contact
}