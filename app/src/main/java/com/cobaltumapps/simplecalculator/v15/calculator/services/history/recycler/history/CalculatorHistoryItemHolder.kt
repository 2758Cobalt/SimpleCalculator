package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.history

import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

/** Класс, реализующий элемент списка истории */
class CalculatorHistoryItemHolder(private val itemBinding: RecyclerHistoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private var currentExpression = ""

    fun bind(history: History) {
        currentExpression = history.user_expression
        with(itemBinding) {
            historyExpressionField.text = history.user_expression
            historyResultField.text = history.result_calculation
        }
    }

    fun bindOnClickHistoryItem(historyItemListener: HolderOnClickListener?) {
        itemBinding.historyContainer.setOnClickListener { historyItemListener?.onHistoryItemClicked(currentExpression) }
    }

}