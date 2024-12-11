package com.cobaltumapps.simplecalculator.v15.sqlite

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.controller.DatabaseController

@Deprecated("Class child of SQLiteOpenHelper is deprecated")
class SQLiteDatabaseControllerLogger: DatabaseController {
    companion object {
        const val LOG_TAG = "SC_SQLiteDatabaseControllerTag"
    }

    override fun insertHistoryData(historyData: HistoryData) {
        Log.d(
            LOG_TAG, "${this.javaClass.simpleName}: inserted history data to the database." +
                    "\nExpression: ${historyData.expression}" +
                    "\nResult: ${historyData.result}"
        )
    }

    override fun updateHistoryData(historyData: HistoryData, idHistory: Int) {
        Log.d(
            LOG_TAG,
            "${this.javaClass.simpleName}: updated by id: $idHistory history data in the database." +
                    "\nExpression: ${historyData.expression}" +
                    "\nResult: ${historyData.result}"
        )
    }

    override fun getHistoryList(): List<HistoryData> {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: obtained history list from the database.")
        return listOf()
    }

    override fun dropDatabase() {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: the Database has been dropped.")
    }
}