package com.example.task4.ui.utils.ext

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.swipeToDelete(
    onDeleteContactAndShowMessage: (index: Int) -> Unit
) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.END or ItemTouchHelper.START
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onDeleteContactAndShowMessage(viewHolder.adapterPosition)
        }
    }).attachToRecyclerView(this)
}