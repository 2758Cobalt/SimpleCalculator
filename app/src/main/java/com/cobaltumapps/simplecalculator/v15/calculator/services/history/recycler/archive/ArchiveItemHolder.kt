package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.archive

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerArchiveItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

/** Холдер, отображающий информацию о записи, которая была заархивирована пользователем. */
class ArchiveItemHolder(private val itemBinding: RecyclerArchiveItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
{
    private val calendarService = CalendarService()
    var hideDateField: Boolean = false

    fun bind(archivedHistory: ArchivedHistory) {
        itemBinding.apply {
            archiveExpressionField.text = archivedHistory.user_expression
            archiveResultField.text = archivedHistory.result_calculation

            // Отключаем отображения поля даты, если флаг включен
            if (hideDateField)
                archiveDateArchivedField.isVisible = false
            else {
                // Форматируем дату и устанавливаем выходную строку в поле
                archiveDateArchivedField.text =
                    calendarService.getStringFormatDate(
                        unixTimestamp =  archivedHistory.date_time_archived,
                        pattern = "d MMMM, yyyy"
                    )
            }
        }
    }

}