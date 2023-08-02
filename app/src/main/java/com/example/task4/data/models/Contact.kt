package com.example.task4.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact (
    var id: Long = UUID.randomUUID().mostSignificantBits,
    val photo: String,
    val name: String,
    val career: String
) : Parcelable {
    override fun toString(): String {
        return "Contact: id: $id, \nFull name: $name, \nCareer: $career, \nImage: $photo"
    }
}