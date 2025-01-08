package com.cobaltumapps.simplecalculator.v15.fragments.history

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentHistoryDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryAdapterUpdater
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService
import com.google.android.material.bottomsheet.BottomSheetBehavior


/** Фрагмент, цель которого - отображать историю расчётов в виде списка и предоставлять управление над каждым элементом.
 * @param historyAdapter Адаптер, отображающий список расчётов.
 * @param calculatorHistoryController Контроллер, управляющий адаптера отображения списка и контроллера базы данных, для сохранения и удаления записей в БД */

class CalculatorHistoryDisplayFragment(onClickHolderListener: HolderOnClickListener? = null): Fragment(), NumpadBottomBehaviorListener,
    HistoryAdapterUpdater {
    private val binding by lazy { FragmentHistoryDisplayBinding.inflate(layoutInflater) }

    // Адаптер для отображения списка расчётов
    val calculatorHistoryRecyclerAdapter by lazy { CalculatorHistoryRecyclerAdapter(onClickHolderListener, this@CalculatorHistoryDisplayFragment) }

    // Контроллер для управления адаптером и контроллером хранилища расчётов (БД в Room)
    var calculatorHistoryController = CalculatorHistoryController(calculatorHistoryRecyclerAdapter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideHistory()


        binding.apply {
            historyRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = calculatorHistoryRecyclerAdapter
            }

            historyDisplayClearFab.apply {
                setOnClickListener {
                    calculatorHistoryRecyclerAdapter.clearHistory()
                    hideHistory()
                    showHistoryHint()
                }
            }
        }

        hideHistoryList()


        val deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_delete, requireContext().theme)
        val archiveIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_archive_pack, requireContext().theme)

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
                    ItemTouchHelper.LEFT -> calculatorHistoryRecyclerAdapter.removeHistoryItem(viewHolder.bindingAdapterPosition)
                    ItemTouchHelper.RIGHT -> {
                        calculatorHistoryRecyclerAdapter.removeHistoryItem(viewHolder.bindingAdapterPosition)
                        Toast.makeText(context, "Record has been send to archive", Toast.LENGTH_SHORT).show()
                    }
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

                // Swipe to left
                if (dX < 0) {
                    // Drawing the delete icon
                    deleteIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.right - (iconMargin - (dX.toInt() / 10)) - intrinsicWidth
                        val iconRight = itemView.right - (iconMargin - (dX.toInt() / 10))

                        // Установка прозрачности иконки в зависимости от длины вектора dX (length = endTouchX - startTouchX)
                        alpha = if (dX > -255) dX.toInt() * -1 else 255
                        setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        draw(c)
                    }
                }

                // Swipe to right
                else {
                    // Drawing the archive icon
                    archiveIcon?.apply {
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + intrinsicHeight

                        val iconLeft = itemView.left + (iconMargin + (dX.toInt() / 10))
                        val iconRight = iconLeft + intrinsicWidth

                        // Установка прозрачности иконки в зависимости от длины вектора dX (length = endTouchX - startTouchX)
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


    /** Показывает список истории */
    private fun showHistory(forceAnimate: Boolean = false) {
        binding.historyRecyclerView.apply {
            isVisible = true
            AnimationsService.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                1f,
                if (!forceAnimate) DEF_ANIM_DURATION
                else 0L
            )
            showClearHistoryFab()
        }

        loadHistoryList()
    }

    /** Прячет список истории */
    private fun hideHistory(forceAnimate: Boolean = false) {
        binding.historyRecyclerView.apply {
            AnimationsService.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                0f,
                if (!forceAnimate) DEF_ANIM_DURATION
                else 0L) {
                isVisible = false
                hideClearHistoryFab()
            }
        }
    }

    /** Показывает кнопку очистки истории */
    private fun showClearHistoryFab() {
        if (calculatorHistoryRecyclerAdapter.itemCount > 0) {
            binding.historyDisplayClearFab.apply {
                isVisible = true
                AnimationsService.animatePropertyChange(
                    this,
                    Property.alpha.name,
                    this.alpha,
                    1f,
                    DEF_ANIM_DURATION
                )
            }
        }
    }

    /** Прячет кнопку очистки истории */
    private fun hideClearHistoryFab() {
        binding.historyDisplayClearFab.apply {
            AnimationsService.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                0f,
                DEF_ANIM_DURATION
            ) {
                isVisible = false
            }
        }
    }

    /** Показывает подсказку */
    private fun showHistoryHint() {
        binding.historyListHint.isVisible = true
    }

    /** Прячет подсказку */
    private fun hideHistoryHint() {
        binding.historyListHint.isVisible = false
    }

    /** Показывает список */
    private fun showHistoryList() {
        showClearHistoryFab()
        hideHistoryHint()
    }

    /** Прячет список */
    private fun hideHistoryList() {
        hideClearHistoryFab()
        showHistoryHint()
    }

    private fun loadHistoryList() {
        calculatorHistoryController.getHistoryList()
    }

    /** Проверяет размер списка */
    private fun checkListSize(): Boolean {
        val checkResult = calculatorHistoryRecyclerAdapter.getItemList().isEmpty()
        if (checkResult)
            hideHistoryList()
        else
            showHistoryList()

        return checkResult
    }

    override fun updateAdapter() {
        checkListSize()
    }

    override fun onStateNumpadChanged(bottomSheet: View, newState: Int) {
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> hideHistory(true)
            BottomSheetBehavior.STATE_COLLAPSED -> showHistory()
        }
    }

    companion object {
        const val LOG_TAG = "SC_HistoryDisplay" +
                "Tag"
        const val DEF_ANIM_DURATION = 400L
    }
}