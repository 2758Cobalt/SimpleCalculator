package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers.HistoryStorageController

class HistoryServiceController(private val historyRecyclerAdapter: CalculatorHistoryRecyclerAdapter): HistoryController {
    var historyStorageController: HistoryStorageController? = null
    val historyTimeFormatter = HistoryTimeFormatter()

    override fun addHistoryItem(historyData: HistoryData) {
        historyRecyclerAdapter.addHistoryItem(historyData)
    }

    override fun removeHistoryItem(index: Int) {
        historyRecyclerAdapter.removeHistoryItem(index)
    }

    override fun clearHistoryItem() {
        historyRecyclerAdapter.clearHistoryItem()
    }
}