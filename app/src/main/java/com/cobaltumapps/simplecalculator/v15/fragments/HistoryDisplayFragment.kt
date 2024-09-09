package com.cobaltumapps.simplecalculator.v15.fragments

import android.os.Bundle
import android.util.Log
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
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.constants.Properties

class HistoryDisplayFragment(onClickHolderListener: HolderOnClickListener): Fragment() {
    private val binding by lazy { FragmentHistoryDisplayBinding.inflate(layoutInflater) }
    val historyAdapter by lazy { CalculatorHistoryRecyclerAdapter(onClickHolderListener) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    = binding.root

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
                    historyAdapter.clearHistoryList()
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
                            historyAdapter.removeItem(viewHolder.bindingAdapterPosition)
                        }
                    }
                }
            })

        touchHelper.attachToRecyclerView(binding.historyRecyclerView)
    }

    fun showHistory(duration: Long = 400L) {
        binding.apply {
            historyRecyclerView.isVisible = true
            historyDisplayClearFab.isVisible = true
        }
        Log.d(LOG_TAG, "HistoryFragment was showed")
        Animations.animatePropertyChange(binding.historyRecyclerView, Properties.alpha, 0f, 1f, duration)
        Animations.animatePropertyChange(binding.historyDisplayClearFab, Properties.alpha, 0f, 1f, duration)
    }

    fun hideHistory(duration: Long = 400L) {
        Log.d(LOG_TAG, "HistoryFragment was hidden")
        Animations.animatePropertyChange(binding.historyRecyclerView, Properties.alpha, 1f, 0f, duration)
        Animations.animatePropertyChange(binding.historyDisplayClearFab, Properties.alpha, 1f, 0f, duration) {
            binding.apply {
                historyRecyclerView.isVisible = false
                historyDisplayClearFab.isVisible = false
            }
        }
    }

    companion object {
        const val LOG_TAG = "SC_HistoryDisplayTag"
    }
}