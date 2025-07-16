package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.ViewholderSelectorReviewerItemBinding
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo

class ExtraSelectorReviewerViewHolder(val binding: ViewholderSelectorReviewerItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(reviewerModel: ExtraSelectableCalculatorInfo) {
        with(binding) {
            extraSelectorReviewerUnitName.text = reviewerModel.title
            extraSelectorReviewerUnitImage.setImageDrawable(reviewerModel.drawable)
            extraSelectorReviewerUnitPreview.text = reviewerModel.previewValues
        }
    }

    fun bindOnPickItem(onClickListener: View.OnClickListener) {
        binding.root.setOnClickListener { onClickListener.onClick(it) }
    }

}