package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryAdapterUpdater
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener

class CalculatorHistoryRecyclerAdapter(
    private val onClickHistoryListener: HolderOnClickListener? = null,
    private val updaterListener: HistoryAdapterUpdater? = null
): RecyclerView.Adapter<CalculatorHistoryItemHolder>(),
    HistoryController
{
    private var expressionsHistoryList: MutableList<HistoryData> = mutableListOf()

    /** Создаёт макет для каждого холдера */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHistoryItemHolder {
        val binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorHistoryItemHolder(binding)
    }

    /** Возвращает полный список элементов */
    override fun getItemCount(): Int {
        return expressionsHistoryList.size
    }

    /** Привязывает логику для каждого холдера */
    override fun onBindViewHolder(holder: CalculatorHistoryItemHolder, position: Int) {
        val historyObject = expressionsHistoryList[position]
        holder.bind(historyObject)
        holder.bindOnClickHistoryItem(onClickHistoryListener)
    }

    /** Устанавливает новый список истории (если необходимо) */
    fun setNewList(newExpressionList: List<HistoryData>?){
        expressionsHistoryList = newExpressionList?.toMutableList() ?: mutableListOf()
        updaterListener?.updateAdapter()
    }

    fun getItemList(): List<HistoryData> {
        return expressionsHistoryList
    }

    /** Добавляет новый элемент в список выражений */
    override fun addHistoryItem(historyData: HistoryData) {
        expressionsHistoryList.addItem(historyData)
        updaterListener?.updateAdapter()
    }

    /** Удаляет элемент в нужной позиции */
    override fun removeHistoryItem(index: Int) {
        expressionsHistoryList.removeItem(index)
        updaterListener?.updateAdapter()
    }

    override fun getHistoryList(): List<HistoryData> {
        return expressionsHistoryList
    }

    /** Удаляет последний элемент */
    override fun clearHistory() {
        val oldestSize = expressionsHistoryList.size
        expressionsHistoryList.clear()
        notifyItemRangeRemoved(0, oldestSize)
    }

    /* Функции расширения - extensions */
    private fun MutableList<HistoryData>.removeItem(index: Int){
        if (expressionsHistoryList.isNotEmpty()) {
            this.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun MutableList<HistoryData>.addItem(historyData: HistoryData){
        this.add(historyData)
        notifyItemInserted(this.lastIndex)
    }

    companion object {
        const val LOG_TAG = "SC_CalculatorHistory" +
                "Recycler" +
                "Tag"
    }


}

