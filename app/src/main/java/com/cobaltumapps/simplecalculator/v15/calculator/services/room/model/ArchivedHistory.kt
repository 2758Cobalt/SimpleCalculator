package com.cobaltumapps.simplecalculator.v15.calculator.services.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.constants.RoomArchivedHistoryConstants
import java.io.Serializable

@Entity(RoomArchivedHistoryConstants.TABLE_NAME)
data class ArchivedHistory(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var user_expression: String,
    var result_calculation: String,
    var date_time_calculation: Int,
    var date_time_archived: Int
): Serializable
