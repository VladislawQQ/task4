package com.example.task4.ui.main.viewpager.myContacts.multiselect

interface MultiSelectState<T : Any> {
    /**
     * Whether the item with the specified ID is checked or not.
     */
    fun isChecked(item: T): Boolean

    /**
     * The total number of checked items
     */
    val totalCheckedCount: Int
}