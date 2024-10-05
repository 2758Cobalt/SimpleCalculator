package com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData

interface HistoryController {
    fun addExpressionItem(historyData: HistoryData)
    fun updateExpressionItem(historyData: HistoryData, pos: Int)
    fun removeExpressionItem(index: Int)
    fun clearExpressionItem()
}