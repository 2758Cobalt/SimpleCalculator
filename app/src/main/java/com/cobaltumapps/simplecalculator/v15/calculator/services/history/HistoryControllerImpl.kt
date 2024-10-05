package com.cobaltumapps.simplecalculator.v15.calculator.services.history

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.CalculatorHistoryRecyclerAdapter
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers.HistoryStorageController

class HistoryControllerImpl(private val historyRecyclerAdapter: CalculatorHistoryRecyclerAdapter): HistoryController {
    private var historyLogger = HistoryLogger()
    var historyStorageController: HistoryStorageController? = null

    override fun addExpressionItem(historyData: HistoryData) {
        historyRecyclerAdapter.addExpressionItem(historyData)
        historyLogger.addExpressionItem(historyData)
    }

    override fun updateExpressionItem(historyData: HistoryData, pos: Int) {
        historyRecyclerAdapter.updateExpressionItem(historyData, pos)
        historyLogger.updateExpressionItem(historyData, pos)
    }

    override fun removeExpressionItem(index: Int) {
        historyLogger.removeExpressionItem(index)
        historyRecyclerAdapter.removeExpressionItem(index)
    }

    override fun clearExpressionItem() {
        historyRecyclerAdapter.clearExpressionItem()
        historyLogger.clearExpressionItem()
    }
}