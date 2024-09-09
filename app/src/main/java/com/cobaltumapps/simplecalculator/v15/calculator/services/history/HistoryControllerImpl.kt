package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.HistoryStorageController

class HistoryControllerImpl(private val historyRecyclerAdapter: CalculatorHistoryRecyclerAdapter): HistoryController {
    private var historyLogger = HistoryLogger()
    var historyStorageController: HistoryStorageController? = null

    override fun addExpressionItem(historyData: HistoryData) {
        historyLogger.addExpressionItem(historyData)
        historyRecyclerAdapter.addNewItem(historyData)
        historyStorageController?.save(historyData)
    }

    override fun updateExpressionItem(historyData: HistoryData, pos: Int) {
        historyLogger.updateExpressionItem(historyData, pos)
    }

    override fun removeExpressionItem(index: Int) {
        historyLogger.removeExpressionItem(index)
        historyRecyclerAdapter.removeItem(index)
    }

    override fun clearExpressionItem() {
        historyLogger.clearExpressionItem()
        historyRecyclerAdapter.clearHistoryList()
    }
}