package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.DateService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryAdapterUpdater
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

class CalculatorHistoryRecyclerAdapter(
    private val onClickHistoryListener: HolderOnClickListener? = null,
    private val updaterListener: HistoryAdapterUpdater? = null
): RecyclerView.Adapter<CalculatorHistoryItemHolder>(),
    HistoryController
{
    private var historyList: MutableList<History> = mutableListOf()
    private val dateService = DateService()

    /** Создаёт макет для каждого холдера */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHistoryItemHolder {
        val binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorHistoryItemHolder(binding)
    }

    /** Возвращает полный список элементов */
    override fun getItemCount(): Int {
        return historyList.size
    }

    /** Курсор для сохранения дня между элементами */
    private var currentDayCursor = -1

    /** Привязывает логику для каждого холдера */
    override fun onBindViewHolder(holder: CalculatorHistoryItemHolder, position: Int) {
        val historyObject = historyList[position]
        val dayOfItem = dateService.getCalendarDate(historyObject.date_time_calculation).day

        // Отображаем дату в любом случае первому элементу
        if (position == 0)
            holder.hideDateField = false

        if (currentDayCursor >= 0 && position != 0) {

            // Если текущий день (в рамках метода) равен дню создания записи
            if (currentDayCursor == dayOfItem)
                holder.hideDateField = true
        }

        currentDayCursor = dayOfItem

        holder.bind(historyObject)
        holder.bindOnClickHistoryItem(onClickHistoryListener)

        // Сброс значения курсора после бинда последнего элемента
        if (position == historyList.size)
            resetCursorTimeStamp()
    }

    private fun resetCursorTimeStamp() {
        currentDayCursor = -1
    }

    /** Устанавливает новый список истории (если необходимо) */
    fun setNewList(newExpressionList: List<History>?){
        historyList = newExpressionList?.toMutableList() ?: mutableListOf()
        updaterListener?.updateAdapter()
        notifyItemRangeChanged(0, historyList.size)
    }

    /** Возвращает список элементов */
    fun getItemList(): List<History> {
        return historyList
    }

    /** Добавляет новый элемент в список выражений */
    override fun addHistoryItem(history: History) {
        historyList.addItem(history)
        updaterListener?.updateAdapter()
    }

    /** Удаляет элемент в нужной позиции */
    override fun deleteHistoryItem(history: History) {
        historyList.removeItem(history)
        updaterListener?.updateAdapter()
    }

    /** Очищает всю историю */
    override fun clearHistory() {
        historyList.clearList()
        updaterListener?.updateAdapter()
    }

    /** Функции расширения - extensions */
    private fun MutableList<History>.removeItem(history: History){
        if (historyList.isNotEmpty()) {
            val indexAdapterItem = historyList.indexOf(history)
            this.removeAt(indexAdapterItem)
            notifyItemRemoved(indexAdapterItem)
            resetCursorTimeStamp()
        }
    }

    private fun MutableList<History>.addItem(history: History){
        this.add(history)
        notifyItemInserted(this.lastIndex)
        resetCursorTimeStamp()
    }

    private fun MutableList<History>.clearList() {
        this.clear()
        notifyItemRangeRemoved(0,historyList.size)
        resetCursorTimeStamp()
    }

    companion object {
        const val LOG_TAG = "SC_CalculatorHistory" +
                "Recycler" +
                "Tag"
    }
}