package com.cobaltumapps.simplecalculator.v15.sqlite.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cobaltumapps.simplecalculator.v15.sqlite.RoomHistoryData
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: RoomHistoryData)

    @Query("SELECT * FROM SC_history")
    fun getItems(): Flow<List<RoomHistoryData>>
}