package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.unit

import android.animation.ValueAnimator
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.data.extra.calculator_unit.ExtraUnitInfo
import com.cobaltumapps.simplecalculator.databinding.ViewholderCalculatorsFeedItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter.DisplayFormatter
import java.math.BigDecimal

class ExtraCalculatorsFeedViewHolder(val binding: ViewholderCalculatorsFeedItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private val bigDecimalFormatter = BigDecimalFormatter(DisplayFormatter())

    private val onSelectedColor by lazy { context.getColor(R.color.md_theme_primaryContainer) }
    private val onDefaultColor by lazy { context.getColor(R.color.md_theme_surfaceContainer) }

    fun bind(unitCalcInfo: ExtraUnitInfo) {
        with(binding) {
            unitCalculatorUnitName.text = unitCalcInfo.unitName
            unitCalculatorUnitSymbol.text =
                unitCalcInfo.unitPreview ?:
                "${unitCalcInfo.unitName.first()}${unitCalcInfo.unitName.last()}"

            val userEntry = unitCalcInfo.unitValue

            extraCalculatorUnitValue.text = if (userEntry.compareTo(BigDecimal("0.0")) == 0) "0"
            else bigDecimalFormatter.format(userEntry.toString())
        }
    }

    fun bindOnClick(onClickListener: View.OnClickListener) {
        binding.extraCalculatorUnitCard.setOnClickListener { onClickListener.onClick(it) }
    }

    fun getUnitValue(): String = binding.extraCalculatorUnitValue.text.toString()

    fun onSelect() {
        val colorAnim = ValueAnimator.ofArgb(binding.extraCalculatorUnitSymbolCard.cardBackgroundColor.defaultColor, onSelectedColor).apply {
            duration = ANIM_COLOR_DURATION
            addUpdateListener { binding.extraCalculatorUnitSymbolCard.setCardBackgroundColor(it.animatedValue as Int) }
        }

        val elevationAnim = ValueAnimator.ofFloat(binding.extraCalculatorUnitCard.cardElevation, 2f).apply {
            duration = ANIM_COLOR_DURATION
            addUpdateListener { binding.extraCalculatorUnitCard.cardElevation = it.animatedValue as Float }
        }

        colorAnim.start()
        elevationAnim.start()
    }

    fun onDeselect() {
        val colorAnim = ValueAnimator.ofArgb(binding.extraCalculatorUnitSymbolCard.cardBackgroundColor.defaultColor, onDefaultColor).apply {
            duration = ANIM_COLOR_DURATION
            addUpdateListener { binding.extraCalculatorUnitSymbolCard.setCardBackgroundColor(it.animatedValue as Int) }
        }

        val elevationAnim = ValueAnimator.ofFloat(binding.extraCalculatorUnitCard.cardElevation, 0f).apply {
            duration = ANIM_COLOR_DURATION
            addUpdateListener { binding.extraCalculatorUnitCard.cardElevation = it.animatedValue as Float }
        }

        colorAnim.start()
        elevationAnim.start()
    }

    companion object {
        const val ANIM_COLOR_DURATION = 250L
    }
}