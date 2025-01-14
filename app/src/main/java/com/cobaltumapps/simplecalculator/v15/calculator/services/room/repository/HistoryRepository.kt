package com.cobaltumapps.simplecalculator.v15.calculator.services.room.repository

import androidx.lifecycle.LiveData
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao.HistoryDao
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

class HistoryRepository(private val historyDao: HistoryDao) {
    val getAllHistory: LiveData<List<History>> = historyDao.getHistoryList()

    suspend fun insertHistoryItem(history: History) {
        historyDao.insertHistoryItem(history)
    }

    suspend fun deleteHistoryItem(history: History) {
        historyDao.deleteHistoryItem(history)
    }

    suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}