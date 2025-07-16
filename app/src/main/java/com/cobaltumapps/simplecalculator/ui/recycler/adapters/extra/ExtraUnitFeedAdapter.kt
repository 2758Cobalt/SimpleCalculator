package com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.databinding.ViewholderCalculatorsFeedItemBinding
import com.cobaltumapps.simplecalculator.domain.viewmodel.UnitCalculatorViewModel
import com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.reviewer.ExtraUnitsFeedComparator
import com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.ExtraCalculatorsFeedViewHolder

class ExtraUnitFeedAdapter(private val unitCalculatorViewModel: UnitCalculatorViewModel): ListAdapter<ExtraUnitInfo, ExtraCalculatorsFeedViewHolder>(
    ExtraUnitsFeedComparator()
) {
    private var selectedItemIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraCalculatorsFeedViewHolder =
        ExtraCalculatorsFeedViewHolder(
            ViewholderCalculatorsFeedItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ExtraCalculatorsFeedViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        if (position == selectedItemIndex) holder.onSelect()
        else holder.onDeselect()

        holder.bindOnPickItem {
            unitCalculatorViewModel.onSelectedItem(position)
        }

    }

    fun updateSelection(newIndex: Int) {
        val oldIndex = selectedItemIndex
        selectedItemIndex = newIndex
        if (oldIndex != -1) notifyItemChanged(oldIndex)
        if (newIndex != -1) notifyItemChanged(newIndex)
    }

    fun updateValueItemByPos(position: Int, newValue: Float) {
        val updatedList = currentList.toMutableList().apply {
            val oldItem = get(position)
            this[position] = oldItem.copy(unitValue = newValue)
        }

        submitList(updatedList)
    }

}