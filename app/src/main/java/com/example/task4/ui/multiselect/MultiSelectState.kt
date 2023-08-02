package com.example.task4.ui.multiselect

interface MultiSelectState<T : Any> {
    /**
     * Whether the item with the specified ID is checked or not.
     */
    fun isChecked(item: T): Boolean
}