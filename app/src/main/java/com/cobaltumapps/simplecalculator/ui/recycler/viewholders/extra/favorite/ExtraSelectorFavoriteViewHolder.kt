package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.data.extra.recycler.favorite.ExtraSelectorFavoriteModel
import com.cobaltumapps.simplecalculator.databinding.ViewholderSelectorFavoriteItemBinding

class ExtraSelectorFavoriteViewHolder(val binding: ViewholderSelectorFavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(favoriteModel: ExtraSelectorFavoriteModel) {
        with(binding) {
            extraSelectorFavoriteUnitName.text = favoriteModel.name
        }
    }

    fun bindOnPickItem(onClickListener: View.OnClickListener) {
        binding.root.setOnClickListener { onClickListener.onClick(it) }
    }
}