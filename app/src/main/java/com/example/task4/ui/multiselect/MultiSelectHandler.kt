package com.example.task4.ui.multiselect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MultiSelectHandler<T : Any> {
    /**
     * Invert selection for the specified item.
     */
    fun toggle(item: T)

    /**
     * Uncheck all items.
     */
    fun clearAll()

    /**
     * Check the specified item in the list
     */
    fun check(item: T)

    /**
     * Uncheck the specified item in the list.
     */
    fun clear(item: T)


}