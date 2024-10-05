package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageLoader
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteRoomDatabase

class HistoryLoader(private val database: SqliteRoomDatabase): HistoryStorageLoader {
    private val databaseDao by lazy { database.getDao() }

    override fun load(): HistoryData {
        return HistoryData(
            "","")
    }
}