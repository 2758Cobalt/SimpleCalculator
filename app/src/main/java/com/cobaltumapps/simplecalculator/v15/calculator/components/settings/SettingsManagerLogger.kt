package com.cobaltumapps.simplecalculator.v15.calculator.components.settings

import android.content.SharedPreferences
import android.util.Log

class SettingsManagerLogger(private val sharedPreferences: SharedPreferences): Settings {
    override fun getPreferenceCondition(keyName: String, defaultValue: Boolean): Boolean {
        val condition = sharedPreferences.getBoolean(keyName, defaultValue)
        Log.i(
            LOG_TAG, "getPreferenceCondition:\n" +
                    "KeyName: $keyName\n" +
                    "Value: $condition\n" +
                    "Default value: $defaultValue"
        )
        return condition
    }

    override fun setPreferenceCondition(keyName: String, value: Boolean) {
        Log.i(
            LOG_TAG, "setPreferenceCondition:\n" +
                    "KeyName: $keyName\n" +
                    "Value: $value"
        )
    }

    override fun getPreferenceCondition(keyName: String, defaultValue: String): String {
        val source = sharedPreferences.getString(keyName, defaultValue)
        Log.i(
            LOG_TAG, "getPreferenceCondition:\n" +
                    "KeyName: $keyName\n" +
                    "Value: $source\n" +
                    "Default value: $defaultValue"
        )
        return source ?: ""
    }

    override fun setPreferenceCondition(keyName: String, value: String) {
        Log.i(
            LOG_TAG, "setPreferenceCondition:\n" +
                    "KeyName: $keyName\n" +
                    "Value: $value"
        )
    }

    companion object {
        const val LOG_TAG = "SC_SettingManagerLoggerTag"
    }
}