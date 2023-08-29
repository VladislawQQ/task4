package com.example.task4.ui.main.viewpager.myContacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task4.data.contacts.ContactService
import com.example.task4.data.models.Contact
import com.example.task4.ui.main.viewpager.myContacts.model.ContactListItem
import com.example.task4.ui.main.viewpager.myContacts.multiselect.ContactMultiSelectHandler
import com.example.task4.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyContactsViewModel : ViewModel() {

    private val contactService = ContactService()
    private val contactMultiSelectHandler = ContactMultiSelectHandler()

    private val _stateFlow = MutableStateFlow(emptyList<ContactListItem>())
    val stateFlow = _stateFlow.asStateFlow()

    val isMultiselect : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch {
            contactMultiSelectHandler.setItemsFlow(viewModelScope, contactService.contacts)
            val combinedFlow = combine(
                contactService.contacts,
                contactMultiSelectHandler.listen(),
                ::merge
            )
            combinedFlow.collect { flow ->
                _stateFlow.value = flow
            }
        }
    }

    fun setPhoneContacts() = contactService.setPhoneContacts()
    fun deleteContact(index: Int) = contactService.deleteContact(index)
    fun deleteContact(contact: Contact) = contactService.deleteContact(contactService.contacts.value.indexOf(contact))

    /**
     * Delete all selected cats
     */
    fun deleteSelectedItems() {
        viewModelScope.launch {
            val currentMultiChoiceState = contactMultiSelectHandler.listen().first()
            contactService.deleteSelectedItems(currentMultiChoiceState)
        }
    }
    fun restoreContact() = contactService.restoreContact()

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
    fun clearSelectedItems() {
        contactMultiSelectHandler.clearAll()
    }
    fun toggle(contact: ContactListItem) {
        contactMultiSelectHandler.toggle(contact.contact)
    }

    fun contactsIsSelected() : Boolean = contactMultiSelectHandler.totalCheckedCount > 0

    fun swapIsMultiselect() {
        isMultiselect.value = !isMultiselect.value!!
    }

    /**
     * Merge cats from the model scope (singleton) and selection state from the view model scope.
     */
    private fun merge(contacts: List<Contact>, multiSelectState: MultiSelectState<Contact>): List<ContactListItem> {
        return contacts.map { contact ->
            ContactListItem(contact, multiSelectState.isChecked(contact), multiSelectState.totalCheckedCount > 0)
        }
    }
}