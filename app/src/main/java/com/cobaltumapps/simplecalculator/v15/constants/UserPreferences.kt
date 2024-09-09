package com.cobaltumapps.simplecalculator.v15.constants

data class UserPreferences(
    val memoryAutoSave: Boolean = false,
    val saveLastCalculation: Boolean = false,
    val leftMode: Boolean = false,
    val oneHandedMode: Boolean = false,
    val vibrationCallback: Boolean = true
)
