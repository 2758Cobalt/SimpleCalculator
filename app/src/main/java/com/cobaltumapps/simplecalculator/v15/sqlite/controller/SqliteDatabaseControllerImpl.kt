package com.cobaltumapps.simplecalculator.v15.sqlite.controller

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.SQLiteDatabaseControllerLogger
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteDatabaseHelper

class SqliteDatabaseControllerImpl(private val dbHelper: SQLiteOpenHelper): DatabaseController {
    private val sqliteDatabaseControllerLogger = SQLiteDatabaseControllerLogger()

    override fun insertHistoryData(historyData: HistoryData) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cv = ContentValues().apply {

            put(SqliteDatabaseHelper.COLUMN_EXPRESSION, historyData.expression)
            put(SqliteDatabaseHelper.COLUMN_RESULT, historyData.result)
        }

        db.insert(SqliteDatabaseHelper.TABLE_NAME, null, cv)

        sqliteDatabaseControllerLogger.insertHistoryData(historyData)
    }

    override fun getHistoryList(): List<HistoryData> {
        val db : SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(SqliteDatabaseHelper.TABLE_NAME,null,null,null,null,null,null,null)

        val historyDataArray = arrayListOf<HistoryData>()

        try {
            while (cursor.moveToNext()) {
                historyDataArray.add(
                    HistoryData(
                        cursor.getString(1), // Expression
                        cursor.getString(2), // Result
                    )
                )
            }
        } catch (ex: Exception) {
            Log.e(SQLiteDatabaseControllerLogger.LOG_TAG, "${this.javaClass.simpleName}: there was an exception." +
                    "\n$ex")
        } finally {
            cursor.close()
        }

        return historyDataArray
    }

    override fun updateHistoryData(historyData: HistoryData, idHistory: Int) {
        sqliteDatabaseControllerLogger.updateHistoryData(historyData, idHistory)
    }

    override fun dropDatabase() {
        sqliteDatabaseControllerLogger.dropDatabase()
    }
}