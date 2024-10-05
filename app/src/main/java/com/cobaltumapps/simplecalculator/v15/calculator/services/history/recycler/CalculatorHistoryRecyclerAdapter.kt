package com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener

class CalculatorHistoryRecyclerAdapter(private val onClickHistoryListener: HolderOnClickListener? = null): RecyclerView.Adapter<HistoryItemHolder>(),
    HistoryController
{
    private var expressionsHistoryList: MutableList<HistoryData> = mutableListOf()

    // Создаёт макет для каждого холдера
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemHolder {
        val binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryItemHolder(binding)
    }

    // Возвращает полный список элементов
    override fun getItemCount(): Int {
        return expressionsHistoryList.size
    }

    // Привязывает логику для каждого холдера
    override fun onBindViewHolder(holder: HistoryItemHolder, position: Int) {
        val historyObject = expressionsHistoryList[position]
        holder.bind(historyObject)
        holder.bindOnClickHistoryItem(onClickHistoryListener)
    }

    // Устанавливает новый список истории (если необходимо)
    fun setNewList(newExpressionList: MutableList<HistoryData>){
        expressionsHistoryList = newExpressionList
    }

    fun getItemList(): List<HistoryData> {
        return expressionsHistoryList
    }

    override fun addExpressionItem(historyData: HistoryData) {
        expressionsHistoryList.addItem(historyData)
    }

    override fun updateExpressionItem(historyData: HistoryData, pos: Int) {
        TODO("Not yet implemented")
    }

    override fun removeExpressionItem(index: Int) {
        expressionsHistoryList.removeItem(index)
    }

    override fun clearExpressionItem() {
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
        const val LOG_TAG = "SC_CalculatorHistoryRecyclerTag"
        const val STATE_TAG = "SC_InstanceStateCalcHistoryTag"
    }
}

