package com.cobaltumapps.simplecalculator.v15.converter.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerConverterUnitItemBinding
import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitModel

/** Класс холдер, который отображает поле с единицей конвертера */
class ConverterUnitViewHolder(private val itemBinding: RecyclerConverterUnitItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(converterUnitModel: ConverterUnitModel) {
        itemBinding.unitTitleField.text = converterUnitModel.unitName
        itemBinding.unitSymbolField.text = converterUnitModel.unitSpecialSymbol
        itemBinding.unitCalculationResultField.text = converterUnitModel.unitValue.toString()
    }

    fun isSelected(condition: Boolean) {
        itemBinding.unitCardView.apply {
            isSelected = condition
            strokeWidth = if (condition) 8 else 2
        }
    }

}