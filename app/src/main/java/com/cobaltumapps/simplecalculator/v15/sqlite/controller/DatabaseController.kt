package com.cobaltumapps.simplecalculator.v15.sqlite.controller

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData

interface DatabaseController {
    fun insertHistoryData(historyData: HistoryData)
    fun updateHistoryData(historyData: HistoryData, idHistory: Int)
    fun getHistoryList(): List<HistoryData>
    fun dropDatabase()
}