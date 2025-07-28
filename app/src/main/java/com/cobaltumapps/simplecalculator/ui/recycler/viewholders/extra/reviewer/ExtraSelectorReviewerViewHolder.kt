package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.selector.ExtraSelectableCalculatorInfo
import com.cobaltumapps.simplecalculator.databinding.ViewholderSelectorReviewerItemBinding

class ExtraSelectorReviewerViewHolder(val binding: ViewholderSelectorReviewerItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val context by lazy { binding.root.context }

    fun bind(reviewerModel: ExtraSelectableCalculatorInfo) {
        with(binding) {
            extraSelectorReviewerUnitName.text = reviewerModel.title
            extraSelectorReviewerUnitImage.setImageDrawable(ResourcesCompat.getDrawable(
                context.resources, reviewerModel.drawableResId ?: R.drawable.ic_weight, context.theme
            ))
            extraSelectorReviewerUnitPreview.text = reviewerModel.previewValues
        }
    }

    fun bindOnPickItem(onClickListener: View.OnClickListener) {
        binding.root.setOnClickListener { onClickListener.onClick(it) }
    }

}