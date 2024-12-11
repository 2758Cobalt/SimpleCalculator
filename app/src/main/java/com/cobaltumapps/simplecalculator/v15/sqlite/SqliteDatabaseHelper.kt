package com.cobaltumapps.simplecalculator.v15.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.controller.DatabaseController
import com.cobaltumapps.simplecalculator.v15.sqlite.controller.SqliteDatabaseControllerImpl

@Deprecated("Class child of SQLiteOpenHelper is deprecated")
class SqliteDatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), DatabaseController {
    var dbController = SqliteDatabaseControllerImpl(this)

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "SimpleCalculatorHistoryDB.db"

        const val TABLE_NAME = "SC_History"

        const val COLUMN_ID = "id"

        const val COLUMN_EXPRESSION = "expression"
        const val COLUMN_RESULT = "result"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseQueries.queryCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(DatabaseQueries.queryDeleteTableExists)
        onCreate(db)
    }

    override fun insertHistoryData(historyData: HistoryData) {
        dbController.insertHistoryData(historyData)
    }

    override fun updateHistoryData(historyData: HistoryData, idHistory: Int) {
        dbController.updateHistoryData(historyData, idHistory)
    }

    override fun getHistoryList(): List<HistoryData> {
        return dbController.getHistoryList()
    }

    override fun dropDatabase() {
        dbController.dropDatabase()
    }
}

