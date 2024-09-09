package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageLoader
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageSaver

class HistoryStorageController: HistoryStorageSaver,
    HistoryStorageLoader, HistoryStorageCleaner

{
    private val historySaver = HistorySaver()
    private val historyLoader = HistoryLoader()
    private val historyCleaner = HistoryCleaner()
    private val historyStorageLogger = HistoryStorageLogger()

    init {
        Log.d(LOG_TAG, "Instance ${javaClass.simpleName} has been created")
    }

    override fun save(historyData: HistoryData) {
        historySaver.save(historyData)
        historyStorageLogger.save(historyData)
    }

    override fun load(): HistoryData {
        historyStorageLogger.load()
        return historyLoader.load()
    }

    override fun clear() {
        historyCleaner.clear()
        historyStorageLogger.clear()
    }

    companion object {
        const val LOG_TAG = "SC_HistoryStorageControllerTag"
    }
}

class HistoryStorageLogger: HistoryStorageSaver, HistoryStorageLoader, HistoryStorageCleaner {
    companion object {
        const val LOG_TAG = "SC_HistoryStorageLoggerTag"
    }

    override fun save(historyData: HistoryData) {
        Log.d(LOG_TAG, "HistoryStorage: Save the data of history:" +
                "\nExpression: ${historyData.expression}" +
                "\nResult: ${historyData.result}")
    }

    override fun load(): HistoryData {
        Log.d(LOG_TAG, "HistoryStorage: The data of history has been loaded")
        return HistoryData("", "")
    }

    override fun clear() {
        Log.d(LOG_TAG, "HistoryStorage: The data of history has been cleared")
    }

}