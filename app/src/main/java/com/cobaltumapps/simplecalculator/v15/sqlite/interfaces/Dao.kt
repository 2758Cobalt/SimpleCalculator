package com.cobaltumapps.simplecalculator.v15.sqlite.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistoryData
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistory_TableNames
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertItem(vararg historyItem: RoomHistoryData)

    @Query("SELECT * FROM ${RoomHistory_TableNames.scHistoryTable} ORDER BY `id` DESC")
    fun getItems(): Flow<List<RoomHistoryData>>

    @Delete
    suspend fun removeItem(vararg historyItem: RoomHistoryData)
}