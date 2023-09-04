package com.example.messengerApp.di

import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.ContactMultiSelectHandler
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.MultiSelectHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class MultiselectModule {

    @Binds
    @Singleton
    abstract fun bindService(
        multiSelect : ContactMultiSelectHandler
    ) : MultiSelectHandler<Contact>

}