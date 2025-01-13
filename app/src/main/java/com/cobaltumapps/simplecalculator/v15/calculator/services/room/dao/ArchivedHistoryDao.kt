package com.cobaltumapps.simplecalculator.v15.calculator.services.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.ArchivedHistory

@Dao
interface ArchivedHistoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertArchivedHistoryItem(archivedHistory: ArchivedHistory)

    @Query("SELECT * FROM SC_ArchivedHistoryTable ORDER BY id ASC")
    fun getArchivedHistoryList(): LiveData<List<ArchivedHistory>>

    @Delete
    suspend fun deleteArchivedHistoryItem(archivedHistory: ArchivedHistory)

}