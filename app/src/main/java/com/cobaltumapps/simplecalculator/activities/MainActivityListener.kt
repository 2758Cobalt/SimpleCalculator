package com.cobaltumapps.simplecalculator.activities

import com.cobaltumapps.simplecalculator.v15.constants.UserPreferences

interface MainActivityListener {
    fun updatePreferences(newUserPreferences: UserPreferences)
}