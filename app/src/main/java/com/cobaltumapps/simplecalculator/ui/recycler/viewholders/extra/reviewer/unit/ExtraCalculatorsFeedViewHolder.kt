package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.unit

import android.animation.ValueAnimator
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.databinding.ViewholderCalculatorsFeedItemBinding

class ExtraCalculatorsFeedViewHolder(val binding: ViewholderCalculatorsFeedItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    private val onSelectedColor by lazy { context.getColor(R.color.md_theme_primaryContainer) }
    private val onDefaultColor by lazy { context.getColor(R.color.md_theme_surfaceContainer) }

    fun bind(unitCalcInfo: ExtraUnitInfo) {
        with(binding) {
            unitCalculatorUnitName.text = unitCalcInfo.unitName
            unitCalculatorUnitSymbol.text =
                unitCalcInfo.unitPreview ?:
                "${unitCalcInfo.unitName.first()}${unitCalcInfo.unitName.last()}"
            extraCalculatorUnitValue.text = unitCalcInfo.unitValue.toString()
        }
    }

    fun bindOnClick(onClickListener: View.OnClickListener) {
        binding.extraCalculatorUnitCard.setOnClickListener { onClickListener.onClick(it) }
    }

    fun getUnitValue(): String = binding.extraCalculatorUnitValue.text.toString()

    fun onSelect() {
        val colorAnim = ValueAnimator.ofArgb(binding.extraCalculatorUnitSymbolCard.cardBackgroundColor.defaultColor, onSelectedColor)
        colorAnim.duration = 250
        colorAnim.addUpdateListener {
            binding.extraCalculatorUnitSymbolCard.setCardBackgroundColor(it.animatedValue as Int)
        }
        colorAnim.start()
    }

    fun onDeselect() {
        val colorAnim = ValueAnimator.ofArgb(binding.extraCalculatorUnitSymbolCard.cardBackgroundColor.defaultColor, onDefaultColor)
        colorAnim.duration = 250
        colorAnim.addUpdateListener {
            binding.extraCalculatorUnitSymbolCard.setCardBackgroundColor(it.animatedValue as Int)
        }
        colorAnim.start()
    }
}