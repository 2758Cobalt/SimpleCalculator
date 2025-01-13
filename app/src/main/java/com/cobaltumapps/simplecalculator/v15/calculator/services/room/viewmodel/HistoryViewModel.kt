package com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.database.HistoryDatabase
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {
    val historyList: LiveData<List<History>>
    private val repository: HistoryRepository

    init {
        val historyDao = HistoryDatabase.getDatabase(application).getHistoryDao()
        repository = HistoryRepository(historyDao)
        historyList = repository.getAllHistory
    }

    fun insertHistoryItem(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHistoryItem(history)
        }
    }

    fun deleteHistoryItem(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHistoryItem(history)
        }
    }
}