package com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository

import androidx.lifecycle.LiveData
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.dao.HistoryDao

class HistoryRepository(private val historyDao: HistoryDao) {
    val getHistoryList: LiveData<List<HistoryEntity>> = historyDao.getHistoryList()

    suspend fun addHistoryItem(historyEntity: HistoryEntity) {
        historyDao.insertHistoryItem(historyEntity)
    }
}