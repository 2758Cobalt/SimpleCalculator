package com.cobaltumapps.simplecalculator.v15.calculator.services.room.repository

import androidx.lifecycle.LiveData
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao.ArchivedHistoryDao
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

class ArchivedHistoryRepository(private val archivedHistoryDao: ArchivedHistoryDao) {
    val getAllArchivedHistory: LiveData<List<ArchivedHistory>> = archivedHistoryDao.getArchivedHistoryList()

    suspend fun insertArchivedHistoryItem(archivedHistory: ArchivedHistory) {
        archivedHistoryDao.insertArchivedHistoryItem(archivedHistory)
    }

    suspend fun deleteArchivedHistoryItem(archivedHistory: ArchivedHistory) {
        archivedHistoryDao.deleteArchivedHistoryItem(archivedHistory)
    }
}