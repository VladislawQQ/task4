package com.example.task4.ui.main.viewpager.myProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MyProfileViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = MyProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val userEmail = args.email
}