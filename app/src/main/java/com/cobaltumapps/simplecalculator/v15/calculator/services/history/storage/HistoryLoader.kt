package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageLoader

class HistoryLoader: HistoryStorageLoader {

    override fun load(): HistoryData {

        return HistoryData(
            "","")
    }
}