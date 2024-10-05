package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage

import com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.interfaces.HistoryStorageCleaner
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteDatabaseHelper

class HistoryCleaner(private val dbHelper: SqliteDatabaseHelper): HistoryStorageCleaner {
    override fun clear() {
        dbHelper.dropDatabase()
    }
}