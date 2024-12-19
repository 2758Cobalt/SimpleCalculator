package com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData

interface HistoryController {
    fun addHistoryItem(historyData: HistoryData)
    fun removeHistoryItem(index: Int)
    fun clearHistory()
}