package com.cobaltumapps.simplecalculator.v15.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.controller.DatabaseController
import com.cobaltumapps.simplecalculator.v15.sqlite.controller.SqliteDatabaseControllerImpl

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

class SQLiteDatabaseControllerLogger: DatabaseController {
    companion object {
        const val LOG_TAG = "SC_SQLiteDatabaseControllerTag"
    }

    override fun insertHistoryData(historyData: HistoryData) {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: inserted history data to the database." +
                "\nExpression: ${historyData.expression}" +
                "\nResult: ${historyData.result}")
    }

    override fun updateHistoryData(historyData: HistoryData, idHistory: Int) {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: updated by id: $idHistory history data in the database." +
                "\nExpression: ${historyData.expression}" +
                "\nResult: ${historyData.result}")
    }

    override fun getHistoryList(): List<HistoryData> {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: obtained history list from the database.")
        return listOf()
    }

    override fun dropDatabase() {
        Log.d(LOG_TAG, "${this.javaClass.simpleName}: the Database has been dropped.")
    }
}

