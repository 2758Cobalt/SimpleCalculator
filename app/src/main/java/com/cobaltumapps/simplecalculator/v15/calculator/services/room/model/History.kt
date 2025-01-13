package com.cobaltumapps.simplecalculator.v15.calculator.services.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.constants.RoomHistoryConstants
import java.io.Serializable

@Entity(RoomHistoryConstants.TABLE_NAME)
data class History(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var user_expression: String,
    var result_calculation: String,
    var date_time_calculation: Int
): Serializable
