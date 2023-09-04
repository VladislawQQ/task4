package com.example.messengerApp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact (
    var id: Long = UUID.randomUUID().mostSignificantBits,
    val photo: String = "",
    val name: String = "",
    val career: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val dateOfBirthday: String = ""
) : Parcelable