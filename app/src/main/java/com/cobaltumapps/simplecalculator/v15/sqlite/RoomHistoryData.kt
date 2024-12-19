package com.cobaltumapps.simplecalculator.v15.sqlite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RoomHistory_TableNames.scHistoryTable)
data class RoomHistoryData(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val expression: String,
    val result: String,
    val dateUnix: Int
)