package com.cobaltumapps.simplecalculator.v15.fragments.history.room.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SC_HistoryTable")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val expression: String = "",
    val calculationResult: String = "",
    val dateTimeUnix: Int = 0
)
