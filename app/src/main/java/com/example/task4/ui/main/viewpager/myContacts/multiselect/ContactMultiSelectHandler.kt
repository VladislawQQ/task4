package com.example.task4.ui.main.viewpager.myContacts.multiselect

import com.example.task4.data.models.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class ContactMultiSelectHandler : MultiSelectHandler<Contact>, MultiSelectState<Contact> {

    private val checkedIds = HashSet<Long>()
    private var items: List<Contact> = emptyList()
    private val stateFlow = MutableStateFlow(OnChanged())
    override fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<Contact>>) {
        coroutineScope.launch {
            itemsFlow.collectLatest { list ->
                items = list
                removeDeletedContacts(list)
                notifyUpdates()
            }
        }
    }
    private fun removeDeletedContacts(list: List<Contact>) {
        val existingIds = HashSet(list.map { it.id })
        val iterator = checkedIds.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next()
            if (!existingIds.contains(id)) {
                iterator.remove()
            }
        }
    }

    override fun listen(): Flow<MultiSelectState<Contact>> {
        return stateFlow.map { this }
    }

    override fun isChecked(item: Contact): Boolean {
        return checkedIds.contains(item.id)
    }
    override fun toggle(item: Contact) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun clear(item: Contact) {
        if (!exists(item)) return

        checkedIds.remove(item.id)

        notifyUpdates()
    }

    override fun check(item: Contact) {
        if (!exists(item)) return

        checkedIds.add(item.id)

        notifyUpdates()
    }

    override val totalCheckedCount: Int
        get() = checkedIds.size

    private fun exists(item: Contact): Boolean {
        return items.any { it.id == item.id }
    }

    override fun clearAll() {
        checkedIds.clear()

        notifyUpdates()
    }

    private fun notifyUpdates() {
        stateFlow.value = OnChanged()
    }

    private class OnChanged
}