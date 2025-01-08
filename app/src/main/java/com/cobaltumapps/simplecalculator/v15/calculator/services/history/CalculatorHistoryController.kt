package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
/** Контроллер, управляющий адаптера отображения списка и контроллера базы данных, для сохранения и удаления записей в БД */
class CalculatorHistoryController(private val historyRecyclerAdapter: CalculatorHistoryRecyclerAdapter): HistoryController {

    override fun addHistoryItem(historyData: HistoryData) {
        historyRecyclerAdapter.addHistoryItem(historyData)
    }

    override fun removeHistoryItem(index: Int) {
        historyRecyclerAdapter.removeHistoryItem(index)
    }

    override fun getHistoryList(): List<HistoryData> {
        return listOf()
    }

    override fun clearHistory() {
        historyRecyclerAdapter.clearHistory()
    }
}