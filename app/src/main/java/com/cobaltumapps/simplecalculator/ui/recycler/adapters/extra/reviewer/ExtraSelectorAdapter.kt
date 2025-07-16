package com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.reviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.databinding.ViewholderSelectorReviewerItemBinding
import com.cobaltumapps.simplecalculator.ui.fragments.extra.calculators.interfaces.ExtraSelector
import com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.reviewer.ExtraSelectorReviewerComparator
import com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.ExtraSelectorReviewerViewHolder

class ExtraSelectorAdapter(val selectorListener: ExtraSelector): ListAdapter<ExtraSelectableCalculatorInfo, ExtraSelectorReviewerViewHolder>(
    ExtraSelectorReviewerComparator()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraSelectorReviewerViewHolder =
        ExtraSelectorReviewerViewHolder(
            ViewholderSelectorReviewerItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ExtraSelectorReviewerViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
        holder.bindOnPickItem { selectorListener.onSelectedItem(item) }
    }

}