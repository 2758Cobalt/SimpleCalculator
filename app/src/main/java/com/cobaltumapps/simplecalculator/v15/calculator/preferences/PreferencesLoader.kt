package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.references.PreferenceKeys
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesLoaderListener

class PreferencesLoader(
    private val sharedPreferences: SharedPreferences,
    private val listener: PreferencesLoaderListener? = null): PreferencesLoaderListener {

    override fun loadData(): PreferencesUserData {
        val loadedConfig =
        with(sharedPreferences) {
            PreferencesUserData(
                getBoolean(PreferenceKeys.keyMemoryAutoSave, false),
                getBoolean(PreferenceKeys.keyKeepLastRecord, true),
                getBoolean(PreferenceKeys.keyLeftHandMode, false),
                getBoolean(PreferenceKeys.keyOneHandedMode, false),
                getBoolean(PreferenceKeys.keyAllowVibration, true)
            )
        }
        listener?.loadData()
        return loadedConfig
    }

    fun loadPreference(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }
}