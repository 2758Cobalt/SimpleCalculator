package com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertHistoryItem(history: History)

    @Update
    suspend fun updateHistoryItem(history: History)

    @Query("SELECT * FROM SC_HistoryTable ORDER BY id ASC")
    fun getHistoryList(): LiveData<List<History>>

    @Delete
    suspend fun deleteHistoryItem(history: History)

}