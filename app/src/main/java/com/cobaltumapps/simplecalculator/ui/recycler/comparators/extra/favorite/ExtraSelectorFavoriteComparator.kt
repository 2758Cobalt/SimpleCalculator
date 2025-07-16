package com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.favorite

import androidx.recyclerview.widget.DiffUtil
import com.cobaltumapps.simplecalculator.data.extra.recycler.favorite.ExtraSelectorFavoriteModel

class ExtraSelectorFavoriteComparator: DiffUtil.ItemCallback<ExtraSelectorFavoriteModel>() {
    override fun areItemsTheSame(oldItem: ExtraSelectorFavoriteModel, newItem: ExtraSelectorFavoriteModel): Boolean
    = oldItem.type == newItem.type

    override fun areContentsTheSame(oldItem: ExtraSelectorFavoriteModel, newItem: ExtraSelectorFavoriteModel): Boolean
    = newItem.name == oldItem.name
}