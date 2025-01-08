package com.cobaltumapps.simplecalculator.v15.fragments.history

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class HistoryRecordTouchCallback(
    private var listener: HistoryRecordTouchListener? = null
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT), HistoryRecordTouchListener
{

    /** Обработка перетаскивания */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    /** Обработка свайпа */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            onSwipedLeft()
        } else if (direction == ItemTouchHelper.RIGHT) {
            onSwipedRight()
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean) {
        // Draw the red delete background
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        viewHolder.itemView.alpha = dX
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    override fun onSwipedLeft() {
        listener?.onSwipedLeft()
    }

    override fun onSwipedRight() {
        listener?.onSwipedRight()
    }
}