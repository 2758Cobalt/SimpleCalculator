package com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData

interface PreferencesUpdaterListener {
    fun updatePreferences(newPreferencesData: PreferencesUserData)
}