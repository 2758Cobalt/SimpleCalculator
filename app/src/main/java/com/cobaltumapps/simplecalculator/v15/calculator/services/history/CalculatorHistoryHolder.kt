package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding

// Класс, реализующий объект истории
class CalculatorHistoryHolder(private val itemBinding: RecyclerHistoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(expression: String){
        itemBinding.historyExpressionField.text = expression
    }

}