package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers

import android.content.Context
import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.HistoryCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.HistoryLoader
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.HistorySaver
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageLoader
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageSaver
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteDatabaseHelper
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteRoomDatabase

class HistoryStorageController(context: Context?): HistoryStorageSaver,
    HistoryStorageLoader, HistoryStorageCleaner

{
    private val roomSqliteManager by lazy { SqliteRoomDatabase.getDatabase(context!!) }
    private val sqliteDatabaseHelper = SqliteDatabaseHelper(context)
    private val historyStorageLogger = HistoryStorageLogger()

    private val historySaver = HistorySaver(roomSqliteManager)
    private val historyLoader = HistoryLoader(roomSqliteManager)
    private val historyCleaner = HistoryCleaner(sqliteDatabaseHelper)


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

