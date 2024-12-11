package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesLoaderListener
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys

class PreferencesLoader(
    private val sharedPreferences: SharedPreferences,
    private val listener: PreferencesLoaderListener? = null): PreferencesLoaderListener {

    override fun loadData(): PreferencesUserData {
        val loadedConfig =
        with(sharedPreferences) {
            PreferencesUserData(
                getBoolean(PreferencesKeys.keyMemoryAutoSave, false),
                getBoolean(PreferencesKeys.keyKeepLastRecord, true),
                getBoolean(PreferencesKeys.keyAllowVibration, true),
                getLong(PreferencesKeys.keyVibrationStrength, 1L)
            )
        }

        listener?.loadData()
        return loadedConfig
    }

    fun loadPreference(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }
}