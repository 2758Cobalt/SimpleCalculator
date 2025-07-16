package com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.reviewer

import androidx.recyclerview.widget.DiffUtil
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo

class ExtraUnitsFeedComparator: DiffUtil.ItemCallback<ExtraUnitInfo>() {
    override fun areItemsTheSame(oldItem: ExtraUnitInfo, newItem: ExtraUnitInfo): Boolean
    = oldItem.unitName == newItem.unitName

    override fun areContentsTheSame(oldItem: ExtraUnitInfo, newItem: ExtraUnitInfo): Boolean
    = newItem == oldItem
}