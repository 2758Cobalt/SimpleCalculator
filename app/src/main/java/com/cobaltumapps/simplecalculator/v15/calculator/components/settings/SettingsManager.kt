package com.cobaltumapps.simplecalculator.v15.calculator.components.settings

import android.content.SharedPreferences
import androidx.core.content.edit


class SettingsManager(
    private val sharedPreferences: SharedPreferences
): Settings {
    private val settingsManagerLogger = SettingsManagerLogger(sharedPreferences)

    override fun getPreferenceCondition(keyName: String, defaultValue: Boolean): Boolean {
        val condition = sharedPreferences.getBoolean(keyName, defaultValue)
        settingsManagerLogger.getPreferenceCondition(keyName, defaultValue)
        return condition
    }

    override fun setPreferenceCondition(keyName: String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(keyName, value)
            settingsManagerLogger.setPreferenceCondition(keyName, value)
        }
    }

    override fun getPreferenceCondition(keyName: String, defaultValue: String): String {
        val source = sharedPreferences.getString(keyName, defaultValue)
        settingsManagerLogger.getPreferenceCondition(keyName, defaultValue)
        return source ?: ""
    }

    override fun setPreferenceCondition(keyName: String, value: String) {
        sharedPreferences.edit {
            putString(keyName, value)
            settingsManagerLogger.setPreferenceCondition(keyName, value)
        }
    }
}

