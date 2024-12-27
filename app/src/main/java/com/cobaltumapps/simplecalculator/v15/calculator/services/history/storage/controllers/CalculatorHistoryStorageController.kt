package com.cobaltumapps.simplecalculator.v15.calculator.services.history.storage.controllers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.data.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HistoryController
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.SqliteRoomDatabase
import kotlinx.coroutines.launch

/** Контроллер, контролирующий хранение историю расчётов в базе данных SQLite с использованием библиотеки Room.
 * @param context Необходимый для работы с Room (нужен для работы с Room).
 * @param historyLifecycle Необходимый для выполнения операций с БД Room на отдельном потоке (нужен для работы с Room). */

class CalculatorHistoryStorageController(val context: Context, val historyLifecycleOwner: LifecycleOwner): HistoryController
{
    private val roomSqliteManager by lazy { SqliteRoomDatabase.getDatabase(context) }
    private val roomDBInstance by lazy { roomSqliteManager.getDao() }

    private val historyLifecycleScope by lazy { historyLifecycleOwner.lifecycleScope }

    init {
        Log.d(LOG_TAG, "Instance ${javaClass.simpleName} has been created")
    }

    override fun addHistoryItem(historyData: HistoryData) {
        historyLifecycleScope.launch {
            roomDBInstance.insertItem(RoomHistoryData(null, historyData.expression, historyData.result, historyData.dateTimeUnix))
        }
    }

    override fun removeHistoryItem(index: Int) {
        historyLifecycleScope.launch {
            roomDBInstance.removeItem()
        }
    }

    override fun getHistoryList(): List<HistoryData> {
        val historyRoomList = mutableListOf<HistoryData>()
        roomDBInstance.getItems().asLiveData().observe(historyLifecycleOwner) { data ->
            data.forEach {
                historyRoomList.add(HistoryData(it.expression, it.result, it.dateUnix))
            }
        }
        return historyRoomList
    }

    override fun clearHistory() {
        context.deleteDatabase("SC_CalculatorHistoryDB.db")
    }

    companion object {
        const val LOG_TAG = "SC_HistoryStorageController" +
                "Tag"
    }
}

