package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.archive

import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerArchiveItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

/** Холдер, отображающий информацию о записи, которая была заархивирована пользователем. */
class ArchiveItemHolder(private val itemBinding: RecyclerArchiveItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(archivedHistory: ArchivedHistory) {
        itemBinding.apply {
            archiveExpressionField.text = archivedHistory.user_expression
            archiveResultField.text = archivedHistory.result_calculation
            archiveItemDate.text = archivedHistory.date_time_calculation.toString()
            archiveItemDateArchived.text = archivedHistory.date_time_archived.toString()
        }
    }

}