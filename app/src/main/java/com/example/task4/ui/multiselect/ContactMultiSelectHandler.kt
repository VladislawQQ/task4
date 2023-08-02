package com.example.task4.ui.multiselect

import com.example.task4.data.models.Contact
import com.example.task4.ui.utils.ext.logExt

open class ContactMultiSelectHandler : MultiSelectHandler<Contact>, MultiSelectState<Contact> {

    private val checkedIds = HashSet<Long>()

    override fun toggle(item: Contact) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun isChecked(item: Contact): Boolean {
        logExt(checkedIds.toString())
        return checkedIds.contains(item.id)
    }

    override fun clear(item: Contact) {
        checkedIds.remove(item.id)
    }

    override fun check(item: Contact) {
        checkedIds.add(item.id)
    }

    override fun clearAll() {
        checkedIds.clear()
    }

    fun sizeOfSelectedItems() = checkedIds.size
}