package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageLoader
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageSaver

class HistoryStorageLogger: HistoryStorageSaver, HistoryStorageLoader, HistoryStorageCleaner {
    companion object {
        const val LOG_TAG = "SC_HistoryStorageLoggerTag"
    }

    override fun save(historyData: HistoryData) {
        Log.d(
            LOG_TAG, "HistoryStorage: Save the data of history:" +
                    "\nExpression: ${historyData.expression}" +
                    "\nResult: ${historyData.result}"
        )
    }

    override fun load(): HistoryData {
        Log.d(LOG_TAG, "HistoryStorage: The data of history has been loaded")
        return HistoryData("", "")
    }

    override fun clear() {
        Log.d(LOG_TAG, "HistoryStorage: The data of history has been cleared")
    }

}