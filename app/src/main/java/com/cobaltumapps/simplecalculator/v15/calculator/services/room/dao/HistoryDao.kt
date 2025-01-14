package com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertHistoryItem(history: History)

    @Query("SELECT * FROM SC_HistoryTable ORDER BY id ASC")
    fun getHistoryList(): LiveData<List<History>>

    @Query("DELETE FROM SC_HistoryTable")
    suspend fun clearHistory()

    @Delete
    suspend fun deleteHistoryItem(history: History)

}