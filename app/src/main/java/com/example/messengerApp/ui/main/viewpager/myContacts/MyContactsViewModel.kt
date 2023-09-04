package com.example.messengerApp.ui.main.viewpager.myContacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.data.repository.interfaces.ContactService
import com.example.messengerApp.ui.main.viewpager.myContacts.model.ContactListItem
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.MultiSelectHandler
import com.example.messengerApp.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyContactsViewModel @Inject constructor(
    private val contactService : ContactService,
    private val contactMultiSelectHandler : MultiSelectHandler<Contact>
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(emptyList<ContactListItem>())
    val stateFlow = _stateFlow.asStateFlow()

    val isMultiselect : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch {
            this@MyContactsViewModel.contactMultiSelectHandler.setItemsFlow(viewModelScope, this@MyContactsViewModel.contactService.contacts)
            val combinedFlow = combine(
                this@MyContactsViewModel.contactService.contacts,
                this@MyContactsViewModel.contactMultiSelectHandler.listen(),
                ::merge
            )
            combinedFlow.collect { flow ->
                _stateFlow.value = flow
            }
        }
    }

    fun setPhoneContacts() = this.contactService.setPhoneContacts()
    fun deleteContact(index: Int) = this.contactService.deleteContact(index)
    fun deleteContact(contact: Contact) = this.contactService.deleteContact(this.contactService.contacts.value.indexOf(contact))

    /**
     * Delete all selected contacts
     */
    fun deleteSelectedItems() {
        viewModelScope.launch {
            val currentMultiChoiceState = this@MyContactsViewModel.contactMultiSelectHandler.listen().first()
            this@MyContactsViewModel.contactService.deleteSelectedItems(currentMultiChoiceState)
        }

        swapIsMultiselect()
        clearSelectedItems()
    }
    fun restoreContact() = this.contactService.restoreContact()

    fun addContact(contact: Contact) {
        this.contactService.addContact(contact)
    }
    fun clearSelectedItems() {
        this.contactMultiSelectHandler.clearAll()
    }
    fun toggle(contact: ContactListItem) {
        this.contactMultiSelectHandler.toggle(contact.contact)
    }

    fun contactsIsSelected() : Boolean = this.contactMultiSelectHandler.totalCheckedCount > 0

    fun swapIsMultiselect() {
        isMultiselect.value = isMultiselect.value == false
    }

    /**
     * Merge contacts from the model scope (singleton) and selection state from the view model scope.
     */
    private fun merge(contacts: List<Contact>, multiSelectState: MultiSelectState<Contact>): List<ContactListItem> {
        return contacts.map { contact ->
            ContactListItem(contact, multiSelectState.isChecked(contact), multiSelectState.totalCheckedCount > 0)
        }
    }
}