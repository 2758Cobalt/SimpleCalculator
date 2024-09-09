package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler

import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding

interface HolderOnClickListener {
    fun onHistoryItemClicked(expression: String)
}

// Класс, реализующий объект истории
class HistoryItemHolder(private val itemBinding: RecyclerHistoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private var currentExpression = ""

    fun bind(historyData: HistoryData) {
        currentExpression = historyData.expression
        itemBinding.historyExpressionField.text = historyData.expression
        itemBinding.historyResultField.text = historyData.result
    }

    fun bindOnClickHistoryItem(historyItemListener: HolderOnClickListener?) {
        itemBinding.historyCard.setOnClickListener { historyItemListener?.onHistoryItemClicked(currentExpression) }
    }

}