package com.cobaltumapps.simplecalculator.v15.sqlite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SC_history")
data class RoomHistoryData(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = RoomHistory_ColumnNames.expression)
    var expression: String,

    @ColumnInfo(name = RoomHistory_ColumnNames.expressionResult)
    var result: String,

    @ColumnInfo(name = RoomHistory_ColumnNames.expressionDate)
    var date: Int
)