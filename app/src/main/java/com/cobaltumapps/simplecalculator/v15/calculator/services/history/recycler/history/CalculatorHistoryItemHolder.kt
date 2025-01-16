package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.history

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

// Класс, реализующий объект истории
class CalculatorHistoryItemHolder(private val itemBinding: RecyclerHistoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private var currentExpression = ""

    private val calendarService = CalendarService()
    var hideDateField: Boolean = false

    fun bind(history: History) {
        currentExpression = history.user_expression
        itemBinding.apply {
            historyExpressionField.text = history.user_expression
            historyResultField.text = history.result_calculation

            // Отключаем отображения поля даты, если флаг включен
            if (hideDateField)
                historyDateCalculationField.isVisible = false
            else {
                // Форматируем дату и устанавливаем выходную строку в поле
                historyDateCalculationField.text =
                    calendarService.getStringFormatDate(
                        unixTimestamp =  history.date_time_calculation,
                        pattern = "d MMMM, yyyy"
                    )
            }
        }
    }

    fun bindOnClickHistoryItem(historyItemListener: HolderOnClickListener?) {
        itemBinding.historyCard.setOnClickListener { historyItemListener?.onHistoryItemClicked(currentExpression) }
    }

}