package com.cobaltumapps.simplecalculator.v15.calculator.services.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.database.HistoryDatabase
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.repository.ArchivedHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchivedHistoryViewModel(application: Application): AndroidViewModel(application) {
    val archivedHistoryList: LiveData<List<ArchivedHistory>>
    private val repository: ArchivedHistoryRepository
    private val calendarService = CalendarService()

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

    fun insertArchivedHistoryItem(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertArchivedHistoryItem(
                ArchivedHistory(
                    null,
                    history.user_expression,
                    history.result_calculation,
                    history.date_time_calculation,
                    calendarService.getUnixTime()
                )
            )
        }
    }

    fun deleteArchivedHistoryItem(archivedHistory: ArchivedHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteArchivedHistoryItem(archivedHistory)
        }
    }

    fun clearArchivedHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearArchivedHistory()
        }
    }
}