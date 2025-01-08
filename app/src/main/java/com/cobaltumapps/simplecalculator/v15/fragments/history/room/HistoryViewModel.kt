package com.cobaltumapps.simplecalculator.v15.fragments.history.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.database.HistoryDatabase
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository.HistoryEntity
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {
    private val getHistoryList: LiveData<List<HistoryEntity>>
    private val repository: HistoryRepository

    init {
        val historyDao = HistoryDatabase.getDatabase(application).getHistoryDao()
        repository = HistoryRepository(historyDao)
        getHistoryList = repository.getHistoryList
    }

    fun addHistoryItem(historyEntity: HistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(historyEntity)
        }
    }
}