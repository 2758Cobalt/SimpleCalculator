package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData

interface HistoryStorageLoader {
    fun load(): HistoryData
}