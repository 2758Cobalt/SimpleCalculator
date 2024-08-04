package com.cobaltumapps.simplecalculator.v15.calculator.history

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CalculatorHistoryRecyclerAdapter(
    private val context: Context,
    private val itemsList: List<String>
): RecyclerView.Adapter<CalculatorHistoryItem>() {


    // Создаёт макет для каждого холдера
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHistoryItem {
        TODO("Реализовать создание холдера")
    }

    // Возвращает полный список элементов
    override fun getItemCount(): Int {
        return itemsList.size
    }

    // Привязывает логику для каждого холдера
    override fun onBindViewHolder(holder: CalculatorHistoryItem, position: Int) {
    }

}

