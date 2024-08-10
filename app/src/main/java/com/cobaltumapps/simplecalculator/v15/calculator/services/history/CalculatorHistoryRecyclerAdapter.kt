package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression

class CalculatorHistoryRecyclerAdapter(
    private var expressionsHistoryList: MutableList<Expression>
): RecyclerView.Adapter<CalculatorHistoryHolder>() {

    // Создаёт макет для каждого холдера
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHistoryHolder {
        val binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorHistoryHolder(binding)
    }

    // Возвращает полный список элементов
    override fun getItemCount(): Int {
        return expressionsHistoryList.size
    }

    // Привязывает логику для каждого холдера
    override fun onBindViewHolder(holder: CalculatorHistoryHolder, position: Int) {
        val historyObject = expressionsHistoryList[position]
        holder.bind(historyObject.expression)
    }

    // Функция для обновления отображения в реальном времени
    fun addNewItem(expression: Expression){
        expressionsHistoryList.addItem(expression)
    }

    fun clearHistoryList() {
        for (x in expressionsHistoryList.indices)
            expressionsHistoryList.removeItem(expressionsHistoryList.lastIndex)
    }

    fun setNewItemList(newExpressionList: MutableList<Expression>){
        expressionsHistoryList = newExpressionList
    }

    private fun MutableList<Expression>.removeItem(index: Int){
        if (expressionsHistoryList.isNotEmpty()) {
            this.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun MutableList<Expression>.addItem(expression: Expression){
        this.add(expression)
        notifyItemInserted(this.lastIndex)
    }
}

