package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerArchiveItemBinding
import com.cobaltumapps.simplecalculator.v15.activities.interfaces.ArchiveController
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.DateService
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

/** Адаптер для отображения и хранения списка записей, которые были заархивированы пользователем.*/
class ArchiveRecyclerAdapter: RecyclerView.Adapter<ArchiveItemHolder>(), ArchiveController
{
    private var archivedHistoryList: MutableList<ArchivedHistory> = mutableListOf()
    private val dateService = DateService()

    /** Создаёт макет для каждого холдера */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveItemHolder {
        val binding = RecyclerArchiveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveItemHolder(binding)
    }

    /** Возвращает список элементов */
    override fun getItemCount(): Int {
        return archivedHistoryList.size
    }

    /** Курсор для сохранения дня между элементами */
    private var currentDayCursor = -1

    /** Привязывает логику для каждого холдера */
    override fun onBindViewHolder(holder: ArchiveItemHolder, position: Int) {
        val archiveItem = archivedHistoryList[position]
        val dayOfItem = dateService.getCalendarDate(archiveItem.date_time_archived).day

        if (currentDayCursor >= 0) {

            // Если текущий месяц (в рамках метода) равен месяцу записи
            if (currentDayCursor == dayOfItem)
                holder.hideDateTime = true
        }

        currentDayCursor = dayOfItem
        holder.bind(archiveItem)
    }

    fun setNewList(newList: List<ArchivedHistory>) {
        archivedHistoryList = newList.toMutableList()
        notifyItemRangeChanged(0, archivedHistoryList.size)
    }

    fun getArchiveList(): List<ArchivedHistory> {
        return archivedHistoryList
    }

    override fun insertArchiveItem(archivedHistory: ArchivedHistory) {
        archivedHistoryList.addItem(archivedHistory)
    }

    override fun deleteArchiveItem(archivedHistory: ArchivedHistory) {
        archivedHistoryList.removeItem(archivedHistory)
    }

    override fun clearArchive() {
        archivedHistoryList.clearList()
    }

    /** Функции расширения - extensions */
    private fun MutableList<ArchivedHistory>.removeItem(archivedHistory: ArchivedHistory){
        if (archivedHistoryList.isNotEmpty()) {
            val indexAdapterItem = archivedHistoryList.indexOf(archivedHistory)
            this.removeAt(indexAdapterItem)
            notifyItemRemoved(indexAdapterItem)
        }
    }

    private fun MutableList<ArchivedHistory>.addItem(archivedHistory: ArchivedHistory){
        this.add(archivedHistory)
        notifyItemInserted(this.lastIndex)
    }

    private fun MutableList<ArchivedHistory>.clearList() {
        this.clear()
        notifyItemRangeRemoved(0, archivedHistoryList.size)
    }

    companion object {
        const val LOG_TAG = "SC_CalculatorArchive" +
                "Recycler" +
                "Tag"
    }

}