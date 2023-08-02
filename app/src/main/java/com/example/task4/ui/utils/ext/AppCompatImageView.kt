package com.example.task4.ui.utils.ext

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.task4.R

val GLIDE_OPTIONS = RequestOptions()
    .circleCrop()
    .placeholder(R.drawable.ic_user_avatar)
    .error(R.drawable.ic_user_avatar)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

fun AppCompatImageView.setContactPhoto(
    contactPhotoUri: String = "android.resource://com.example.task3/drawable/profile_photo"
) {
    Glide.with(context)
        .load(contactPhotoUri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

fun AppCompatImageView.setContactPhoto(
    contactPhotoUri: Drawable
) {
    Glide.with(context)
        .load(contactPhotoUri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}