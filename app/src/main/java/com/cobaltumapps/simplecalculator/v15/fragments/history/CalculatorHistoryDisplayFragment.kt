package com.cobaltumapps.simplecalculator.v15.fragments.history

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.databinding.FragmentHistoryDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryAdapterUpdater
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.history.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel.ArchivedHistoryViewModel
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel.HistoryViewModel
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.services.AnimationsService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


/** Фрагмент, цель которого - отображать историю расчётов в виде списка и предоставлять управление над каждым элементом.
 * @param onClickHolderListener Слушатель нажатия элемента в RecyclerView */

class CalculatorHistoryDisplayFragment(onClickHolderListener: HolderOnClickListener? = null): Fragment(), NumpadBottomBehaviorListener,
    HistoryAdapterUpdater, HistoryController {
    private val binding by lazy { FragmentHistoryDisplayBinding.inflate(layoutInflater) }

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var archivedHistoryViewModel: ArchivedHistoryViewModel

    private val calendarService = CalendarService()

    // Адаптер для отображения списка расчётов
    private val calculatorHistoryRecyclerAdapter by lazy { CalculatorHistoryRecyclerAdapter(onClickHolderListener, this@CalculatorHistoryDisplayFragment) }

    var calculatorHistoryController = CalculatorHistoryController(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideHistory()

        // Creation ViewModels
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        archivedHistoryViewModel = ViewModelProvider(this)[ArchivedHistoryViewModel::class.java]

        binding.apply {
            historyRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = calculatorHistoryRecyclerAdapter
            }

            historyDisplayClearFab.apply {
                setOnClickListener {
                    clearAllHistory()
                }
            }

            historyDisplayArchiveAllFab.apply {
                setOnClickListener {
                    storeAllHistoryToArchive()
                }
            }
        }

        hideHistoryList()

        val deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_delete, requireContext().theme)
        val archiveIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_archive, requireContext().theme)

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
                    ItemTouchHelper.LEFT -> deleteHistoryItem(
                        calculatorHistoryRecyclerAdapter.getItemList()[viewHolder.bindingAdapterPosition]
                    )
                    ItemTouchHelper.RIGHT -> {
                        val historyItem = calculatorHistoryRecyclerAdapter.getItemList()[viewHolder.bindingAdapterPosition]

                        // Добавляем в архив запись, которую выбрали
                        archivedHistoryViewModel.insertArchivedHistoryItem(
                            ArchivedHistory(
                                null,
                                historyItem.user_expression,
                                historyItem.result_calculation,
                                historyItem.date_time_calculation,
                                calendarService.getUnixTime()
                            )
                        )

                        // Удаляем запись из обычной истории расчётов
                        deleteHistoryItem(historyItem)

                        // Уведомлеяем пользователя
                        Snackbar.make(binding.root, "This item has been moved to archive", Snackbar.LENGTH_SHORT).show()
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

    /** Перемещает всю историю в архив */
    private fun storeAllHistoryToArchive() {
        alertArchiveAllHistory {
            for (historyItem in calculatorHistoryRecyclerAdapter.getItemList()) {
                archivedHistoryViewModel.insertArchivedHistoryItem(
                    ArchivedHistory(
                        null,
                        historyItem.user_expression,
                        historyItem.result_calculation,
                        historyItem.date_time_calculation,
                        calendarService.getUnixTime()
                    )
                )
            }
            hideHistory()
            showHistoryList()
            clearHistory()

            Snackbar.make(binding.root, "History has been moved to archive", Snackbar.LENGTH_SHORT)
                .setAction("Done") {
                }
                .show()
        }
    }

    private fun clearAllHistory() {
        alertHistoryClearAll {
            hideHistory()
            showHistoryHint()
            clearHistory()

            Snackbar.make(binding.root, "History has been deleted", Snackbar.LENGTH_SHORT)
                .setAction("Done") {
                }
                .show()
        }
    }

    override fun addHistoryItem(history: History) {
        calculatorHistoryRecyclerAdapter.addHistoryItem(history)
        historyViewModel.insertHistoryItem(history)
    }

    override fun deleteHistoryItem(history: History) {
        calculatorHistoryRecyclerAdapter.deleteHistoryItem(history)
        historyViewModel.deleteHistoryItem(history)
    }

    override fun clearHistory() {
        calculatorHistoryRecyclerAdapter.clearHistory()
        historyViewModel.clearHistory()
    }

    /** Показывает список истории */
    private fun showHistory(forceAnimate: Boolean = false) {
        loadHistoryList()
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
            showArchiveHistoryFab()
        }
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
                hideArchiveHistoryFab()
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

    /** Показывает кнопку архивации истории */
    private fun showArchiveHistoryFab() {
        if (calculatorHistoryRecyclerAdapter.itemCount > 0) {
            binding.historyDisplayArchiveAllFab.apply {
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

    /** Прячет кнопку архивации истории */
    private fun hideArchiveHistoryFab() {
        binding.historyDisplayArchiveAllFab.apply {
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
        showArchiveHistoryFab()
        hideHistoryHint()
    }

    /** Прячет список */
    private fun hideHistoryList() {
        hideClearHistoryFab()
        hideArchiveHistoryFab()
        showHistoryHint()
    }

    /** Считывает историю из ViewModel*/
    private fun loadHistoryList() {
        historyViewModel.historyList.observe(viewLifecycleOwner) { history ->
            calculatorHistoryRecyclerAdapter.setNewList(history)
        }
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

    /** Вызов предупредительного диалога */
    private fun alertArchiveAllHistory(onAccepted: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.display_history_dialogAlertTitle)
            .setMessage(R.string.display_history_storeToArchive_dialogMessage)
            .setNeutralButton(R.string.display_history_dialog_cancel)  { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.display_history_dialog_accept) { _, _ ->
                onAccepted.invoke()
            }
            .show()
    }

    private fun alertHistoryClearAll(onAccepted: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.display_history_dialogAlertTitle)
            .setMessage(R.string.display_history_clearAllHistory_dialogMessage)
            .setNeutralButton(R.string.display_history_dialog_cancel)  { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.display_history_dialog_accept) { _, _ ->
                onAccepted.invoke()
            }
            .show()
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