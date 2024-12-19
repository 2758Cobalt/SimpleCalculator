package com.cobaltumapps.simplecalculator.v15.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.FragmentHistoryDisplayBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryAdapterUpdater
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers.CalculatorHistoryStorageController
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
    val calculatorHistoryRecyclerAdapter by lazy { CalculatorHistoryRecyclerAdapter(onClickHolderListener) }

    // Контроллер для управления адаптером и контроллером хранилища расчётов (БД в Room)
    private val calculatorHistoryController = CalculatorHistoryController(calculatorHistoryRecyclerAdapter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        calculatorHistoryController.calculatorHistoryStorageController =
            CalculatorHistoryStorageController(requireContext(), viewLifecycleOwner.lifecycleScope)
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
                    //calculatorHistoryController.calculatorHistoryStorageController?.clear()
                    hideHistory()
                    showHistoryHint()
                }
            }

            calculatorHistoryRecyclerAdapter.updaterListener = this@CalculatorHistoryDisplayFragment
        }

        hideHistoryList()

        val touchHelper = ItemTouchHelper(
            object: ItemTouchHelper.Callback() {
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    return makeMovementFlags(
                        0, ItemTouchHelper.START
                    )
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when(direction) {
                        ItemTouchHelper.START -> {
                            calculatorHistoryRecyclerAdapter.removeHistoryItem(viewHolder.bindingAdapterPosition)
                        }
                    }
                }
            })

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