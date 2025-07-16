package com.cobaltumapps.simplecalculator.ui.recycler.adapters.extra.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.cobaltumapps.simplecalculator.data.extra.recycler.favorite.ExtraSelectorFavoriteModel
import com.cobaltumapps.simplecalculator.databinding.ViewholderSelectorFavoriteItemBinding
import com.cobaltumapps.simplecalculator.ui.recycler.comparators.extra.favorite.ExtraSelectorFavoriteComparator
import com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.favorite.ExtraSelectorFavoriteViewHolder

class ExtraSelectorFavoriteAdapter: ListAdapter<ExtraSelectorFavoriteModel, ExtraSelectorFavoriteViewHolder>(
    ExtraSelectorFavoriteComparator()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraSelectorFavoriteViewHolder =
        ExtraSelectorFavoriteViewHolder(
            ViewholderSelectorFavoriteItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ExtraSelectorFavoriteViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
        holder.bindOnPickItem { TODO("Implement pick item") }
    }

}