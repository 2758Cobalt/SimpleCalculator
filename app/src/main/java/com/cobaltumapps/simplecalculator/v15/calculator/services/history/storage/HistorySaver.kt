package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageSaver
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteRoomDatabase

class HistorySaver(private val database: SqliteRoomDatabase): HistoryStorageSaver {
    private val databaseDao by lazy { database.getDao() }

    override fun save(historyData: HistoryData) {
        Thread {
            val newItem = RoomHistoryData(null, historyData.expression, historyData.result)
            databaseDao.insertItem(newItem)
        }.start()
    }
}