package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteRoomDatabase
import kotlinx.coroutines.launch

/** Контроллер, контролирующий хранение историю расчётов в базе данных SQLite с использованием библиотеки Room.
 * @param context Необходимый для работы с Room (нужен для работы с Room).
 * @param historyLifecycle Необходимый для выполнения операций с БД Room на отдельном потоке (нужен для работы с Room). */

class CalculatorHistoryStorageController(context: Context, val historyLifecycle: LifecycleCoroutineScope): HistoryController
{
    private val roomSqliteManager by lazy { SqliteRoomDatabase.getDatabase(context) }
    private val roomDBInstance by lazy { roomSqliteManager.getDao() }

    init {
        Log.d(LOG_TAG, "Instance ${javaClass.simpleName} has been created")
    }

    override fun addHistoryItem(historyData: HistoryData) {
        historyLifecycle.launch {
            roomDBInstance.insertItem(RoomHistoryData(null, historyData.expression, historyData.result, historyData.dateTimeUnix))
        }
    }

    override fun removeHistoryItem(index: Int) {
    }

    override fun clearHistory() {
    }

    companion object {
        const val LOG_TAG = "SC_HistoryStorageController" +
                "Tag"
    }
}

