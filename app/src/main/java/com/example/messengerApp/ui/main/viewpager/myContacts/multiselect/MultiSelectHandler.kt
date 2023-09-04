package com.example.messengerApp.ui.main.viewpager.myContacts.multiselect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MultiSelectHandler<T : Any> : MultiSelectState<T> {

    /**
     * Set the list flow which will be observed by the handler in order
     * to keep the inner state up-to-date. Usually call this method in the init
     * block of your view-model.
     */
    fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<T>>)

    /**
     * Observe the current state of multi-choice
     */
    fun listen(): Flow<MultiSelectState<T>>

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