package com.cobaltumapps.simplecalculator.v15.calculator.components.settings

import android.content.SharedPreferences

object SettingsSingleton: Settings {
    @Volatile
    private var instance: SettingsManager? = null

    fun getInstance(sharedPreferences: SharedPreferences): SettingsManager {
        return instance ?: synchronized(this) {
            instance ?: SettingsManager(sharedPreferences).also { instance = it }
        }
    }

    override fun getPreferenceCondition(keyName: String, defaultValue: Boolean): Boolean {
        return instance?.getPreferenceCondition(keyName, defaultValue) ?: false
    }

    override fun setPreferenceCondition(keyName: String, value: Boolean) {
        instance?.setPreferenceCondition(keyName, value)
    }

    override fun getPreferenceCondition(keyName: String, defaultValue: String): String {
        return instance?.getPreferenceCondition(keyName, defaultValue) ?: ""
    }

    override fun setPreferenceCondition(keyName: String, value: String) {
        instance?.setPreferenceCondition(keyName, value)
    }

}