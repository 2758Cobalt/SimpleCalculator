package com.cobaltumapps.simplecalculator.v15.calculator.preferences.data

data class PreferencesUserData (
    var memoryAutoSave: Boolean = false,
    var keepLastRecord: Boolean = true,
    var allowVibration: Boolean = true,
    var vibrationStrength: Long = 1L,
)