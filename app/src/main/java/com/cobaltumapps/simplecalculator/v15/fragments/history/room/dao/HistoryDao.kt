package com.cobaltumapps.simplecalculator.v15.fragments.history.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(HistoryEntity::class)
    suspend fun insertHistoryItem(historyEntity: HistoryEntity)

    @Query("SELECT * FROM SC_HistoryTable ORDER BY `id` ASC")
    fun getHistoryList(): LiveData<List<HistoryEntity>>

    @Delete
    suspend fun removeHistoryItem(historyEntity: HistoryEntity)
}