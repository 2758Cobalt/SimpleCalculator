package com.cobaltumapps.simplecalculator.v15.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.FragmentHistoryDisplayBinding
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.HistoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers.HistoryStorageController
import com.cobaltumapps.simplecalculator.v15.constants.Property
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.EngineeringBottomBehaviorListener
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.interfaces.NumpadBottomBehaviorListener
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HistoryDisplayFragment(onClickHolderListener: HolderOnClickListener? = null): Fragment(), NumpadBottomBehaviorListener, EngineeringBottomBehaviorListener {
    private val binding by lazy { FragmentHistoryDisplayBinding.inflate(layoutInflater) }

    val historyAdapter by lazy { CalculatorHistoryRecyclerAdapter(onClickHolderListener) }
    private val historyControllerImpl = HistoryControllerImpl(historyAdapter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        historyControllerImpl.historyStorageController = HistoryStorageController(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideHistory()


        binding.apply {
            historyRecyclerView.apply {
                isVisible = false
                layoutManager = LinearLayoutManager(context)
                adapter = historyAdapter
            }

            with(historyDisplayClearFab) {
                isVisible = false
                setOnClickListener {
                    historyAdapter.clearExpressionItem()
                    historyControllerImpl.historyStorageController?.clear()
                    hideHistory()
                }
            }
        }

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
                            historyAdapter.removeExpressionItem(viewHolder.bindingAdapterPosition)
                        }
                    }
                }
            })

        touchHelper.attachToRecyclerView(binding.historyRecyclerView)
    }

    private fun showHistory() {
        binding.historyRecyclerView.apply {
            isVisible = true
            Animations.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                1f,
                DEF_ANIM_DURATION)

            showClearHistoryFab()
        }
    }

    private fun hideHistory() {
        binding.historyRecyclerView.apply {
            Animations.animatePropertyChange(
                this,
                Property.alpha.name,
                this.alpha,
                0f,
                DEF_ANIM_DURATION) {
                isVisible = false
                hideClearHistoryFab()
            }
        }
    }

    private fun showClearHistoryFab() {
        if (historyAdapter.itemCount > 0) {
            binding.historyDisplayClearFab.apply {
                isVisible = true
                Animations.animatePropertyChange(
                    this,
                    Property.alpha.name,
                    this.alpha,
                    1f,
                    DEF_ANIM_DURATION
                )
            }
        }
    }

    private fun hideClearHistoryFab() {
        binding.historyDisplayClearFab.apply {
            Animations.animatePropertyChange(
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

    override fun onStateEngNumpadChanged(bottomSheet: View, newState: Int) {
    }

    override fun onStateNumpadChanged(bottomSheet: View, newState: Int) {
        when(newState) {
            BottomSheetBehavior.STATE_EXPANDED -> hideHistory()
            BottomSheetBehavior.STATE_COLLAPSED -> showHistory()
        }
    }

    companion object {
        const val LOG_TAG = "SC_HistoryDisplayTag"
        const val DEF_ANIM_DURATION = 400L
    }
}