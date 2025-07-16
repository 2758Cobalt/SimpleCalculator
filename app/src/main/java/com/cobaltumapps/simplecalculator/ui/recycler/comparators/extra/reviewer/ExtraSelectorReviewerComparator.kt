package com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.reviewer

import androidx.recyclerview.widget.DiffUtil
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo

class ExtraSelectorReviewerComparator: DiffUtil.ItemCallback<ExtraSelectableCalculatorInfo>() {
    override fun areItemsTheSame(oldItem: ExtraSelectableCalculatorInfo, newItem: ExtraSelectableCalculatorInfo): Boolean
    = oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: ExtraSelectableCalculatorInfo, newItem: ExtraSelectableCalculatorInfo): Boolean
    = newItem == oldItem
}