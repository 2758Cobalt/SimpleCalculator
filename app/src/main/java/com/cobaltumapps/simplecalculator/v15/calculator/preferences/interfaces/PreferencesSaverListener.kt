package com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData

interface PreferencesSaverListener {
    fun saveData(newData: PreferencesUserData)
}