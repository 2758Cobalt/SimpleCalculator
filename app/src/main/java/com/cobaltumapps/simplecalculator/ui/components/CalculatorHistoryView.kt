package com.cobaltumapps.simplecalculator.ui.components

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.ViewHistoryDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.history.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.ui.fragments.calculator.history.CalculatorHistoryDisplayFragment.Companion.DEF_ANIM_DURATION
import com.cobaltumapps.simplecalculator.ui.fragments.calculator.keyboard.interfaces.NumpadBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CalculatorHistoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet, defAttrSet: Int = 0
): ConstraintLayout(context, attrs, defAttrSet), HistoryController, NumpadBottomBehaviorListener {
    private var binding: ViewHistoryDisplayBinding =
        ViewHistoryDisplayBinding.inflate(LayoutInflater.from(context), this, true)

    interface HistoryOnSwipeListener {
        fun onSwipeArchiveHistoryItem(history: History)
        fun onSwipeDeleteHistoryItem(history: History)
    }

    private var onSwipeListener: HistoryOnSwipeListener? = null
    private val historyAdapter = CalculatorHistoryRecyclerAdapter()

    init {

        /* context.theme.obtainStyledAttributes(attrs, R.styleable, 0, 0).apply {
            try {
            } finally {
                recycle()
            }
        } */

        binding.historyRecyclerView.apply {
            adapter = historyAdapter
        }

        val deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_delete, context.theme)
        val archiveIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_archive, context.theme)

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction) {
                    ItemTouchHelper.LEFT -> onSwipeListener?.onSwipeDeleteHistoryItem(historyAdapter.getItemList()[viewHolder.bindingAdapterPosition])
                    ItemTouchHelper.RIGHT -> onSwipeListener?.onSwipeArchiveHistoryItem(historyAdapter.getItemList()[viewHolder.bindingAdapterPosition])
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                if (dX < 0) {
                    deleteIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.right - (iconMargin - (dX.toInt() / 10)) - intrinsicWidth
                        val iconRight = itemView.right - (iconMargin - (dX.toInt() / 10))

                        alpha = if (dX > -255) dX.toInt() * -1 else 255
                        setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        draw(c)
                    }
                }
                else {
                    archiveIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.left + (iconMargin + (dX.toInt() / 10))
                        val iconRight = iconLeft + intrinsicWidth

                        alpha = if (dX < 255) dX.toInt() else 255
                        setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        draw(c)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val touchHelper = ItemTouchHelper(simpleCallback)
        touchHelper.attachToRecyclerView(binding.historyRecyclerView)
    }

    fun setSwipeListener(listener: HistoryOnSwipeListener) {
        this.onSwipeListener = listener
    }

    fun setNewHistoryList(newList: List<History>) {
        historyAdapter.setNewList(newList)
    }

    override fun addHistoryItem(history: History) {
        historyAdapter.addHistoryItem(history)
    }

    override fun deleteHistoryItem(history: History) {
        historyAdapter.deleteHistoryItem(history)
    }

    override fun clearHistory() {
        historyAdapter.clearHistory()
    }

    fun getAllHistoryEntries(): List<History> {
        return historyAdapter.getItemList()
    }

    private fun checkListSize(): Boolean {
        val checkResult = historyAdapter.getItemList().isEmpty()
        if (checkResult)
            hideHistoryList()
        else
            showHistoryList()

        return checkResult
    }

    /** Update condition of visibility the history hint about  */
    private fun updateHistoryHint() {
        binding.historyListHint.isVisible = historyAdapter.getItemList().isEmpty()
    }

    /** Update condition of visibility the history FAB "Clear" and "ArchiveAll" */
    private fun updateHistoryFABs() {
        with(binding) {
            animateFABVisibility(historyDisplayClear, historyAdapter.getItemList().isNotEmpty())
            animateFABVisibility(historyDisplayArchiveAll, historyAdapter.getItemList().isNotEmpty())
        }
    }

    /** Displays the history list */
    private fun updateHistoryDisplay() {
        updateHistoryFABs()
        updateHistoryHint()
    }

    private fun animateFABVisibility(fabView: View, visibility: Boolean) {
        if (visibility) {
            fabView.isVisible = true
            AnimationsService.animatePropertyChange(fabView, Property.alpha.name, fabView.alpha, 1f, DEF_ANIM_DURATION)
        }
        else AnimationsService.animatePropertyChange(fabView, Property.alpha.name, fabView.alpha, 0f, DEF_ANIM_DURATION) { fabView.isVisible = false }
    }

    private fun showHistoryList(forceAnimate: Boolean = false) {
        binding.historyRecyclerView.apply {
            isVisible = true
            AnimationsService.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                1f,
                if (!forceAnimate) DEF_ANIM_DURATION else 0L
            )
            updateHistoryDisplay()
        }
    }

    /** Прячет список истории */
    private fun hideHistoryList(forceAnimate: Boolean = false) {
        binding.historyRecyclerView.apply {
            AnimationsService.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                0f,
                if (!forceAnimate) DEF_ANIM_DURATION else 0L) {
                isVisible = false
            }
            updateHistoryDisplay()
        }
    }

    fun bindClearHistoryButton(onClickListener: OnClickListener) {
        binding.historyDisplayClear.setOnClickListener { onClickListener.onClick(it) }
    }

    fun bindArchiveHistoryButton(onClickListener: OnClickListener) {
        binding.historyDisplayArchiveAll.setOnClickListener { onClickListener.onClick(it) }
    }

    override fun onStateNumpadChanged(bottomSheet: View, newState: Int) {
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> hideHistoryList(true)
            BottomSheetBehavior.STATE_COLLAPSED -> showHistoryList()
        }
    }
}