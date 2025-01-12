package com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

interface HistoryController {
    fun addHistoryItem(history: History)
    fun updateHistoryItem(history: History)
    fun deleteHistoryItem(history: History)
}