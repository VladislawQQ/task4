package com.example.task4.ui.main.viewpager.myContacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task4.data.contacts.ContactService
import com.example.task4.data.models.Contact
import com.example.task4.ui.main.viewpager.myContacts.multiselect.ContactMultiSelectHandler
import com.example.task4.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MyContactsViewModel : ViewModel() {

    private val contactService = ContactService()
    private val contactMultiSelectHandler = ContactMultiSelectHandler()

    private val _stateFlow = MutableStateFlow<State?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            contactMultiSelectHandler.setItemsFlow(viewModelScope, contactService.contacts)
            val combinedFlow = combine(
                contactService.contacts,
                contactMultiSelectHandler.listen(),
                ::merge
            )
            combinedFlow.collectLatest {
                _stateFlow.value = it
            }
        }
    }

    fun deleteContact(index: Int) = contactService.deleteContact(index)
    fun deleteContact(contact: Contact) = contactService.deleteContact(contactService.contacts.value.indexOf(contact))
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

    /**
     * Merge cats from the model scope (singleton) and selection state from the view model scope.
     */
    private fun merge(contacts: List<Contact>, multiChoiceState: MultiSelectState<Contact>): State {
        return State(
            contacts = contacts.map { contact ->
                ContactListItem(contact, multiChoiceState.isChecked(contact))
            },
            isMultiSelect = multiChoiceState.totalCheckedCount > 0
        )
    }
    class State(
        val contacts: List<ContactListItem>,
        val isMultiSelect: Boolean
    )
}