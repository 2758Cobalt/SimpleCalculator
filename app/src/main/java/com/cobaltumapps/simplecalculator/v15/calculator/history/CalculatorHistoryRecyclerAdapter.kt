package com.cobaltumapps.simplecalculator.v15.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobaltumapps.simplecalculator.databinding.RecyclerHistoryItemBinding

class CalculatorHistoryRecyclerAdapter(
    private val expressionsHistoryList: List<Expression>
): RecyclerView.Adapter<CalculatorHistoryHolder>() {
    private lateinit var binding: RecyclerHistoryItemBinding
    var showOnlyFirstItem = true


    // Создаёт макет для каждого холдера
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHistoryHolder {

        binding = RecyclerHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorHistoryHolder(binding)
    }

    // Возвращает полный список элементов
    override fun getItemCount(): Int {
        return if (showOnlyFirstItem) 1 else expressionsHistoryList.size
    }

    // Привязывает логику для каждого холдера
    override fun onBindViewHolder(holder: CalculatorHistoryHolder, position: Int) {

        val historyObject = expressionsHistoryList[position]

        with(holder){
            with(historyObject){
                binding.historyExpressionField.text = this.expression

            }
        }

    }

    private fun showOnlyFirstItem(showOnlyFirst: Boolean) {
        this.showOnlyFirstItem = showOnlyFirst
        notifyDataSetChanged()

    }

    // Функция для обновления отображения в реальном времени
    fun updateDisplay(showAll: Boolean) {
        showOnlyFirstItem(!showAll)
    }

}