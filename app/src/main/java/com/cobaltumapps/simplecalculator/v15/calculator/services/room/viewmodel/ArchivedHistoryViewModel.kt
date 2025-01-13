package com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.database.HistoryDatabase
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.repository.ArchivedHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchivedHistoryViewModel(application: Application): AndroidViewModel(application) {
    val archivedHistoryList: LiveData<List<ArchivedHistory>>
    private val repository: ArchivedHistoryRepository

    init {
        val archivedHistoryDao = HistoryDatabase.getDatabase(application).getArchivedHistoryDao()
        repository = ArchivedHistoryRepository(archivedHistoryDao)
        archivedHistoryList = repository.getAllArchivedHistory
    }

    fun insertArchivedHistoryItem(archivedHistory: ArchivedHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertArchivedHistoryItem(archivedHistory)
        }
    }

    fun deleteArchivedHistoryItem(archivedHistory: ArchivedHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteArchivedHistoryItem(archivedHistory)
        }
    }
}