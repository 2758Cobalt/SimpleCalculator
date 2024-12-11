package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesSaverListener
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys

class PreferencesSaver(
    private val sharedPreferences: SharedPreferences,
    private val listener: PreferencesSaverListener? = null
) : PreferencesSaverListener {

    /** Сохраняет набор предпочтений */
    override fun saveData(newData: PreferencesUserData) {
        val editor = sharedPreferences.edit()
        with(editor) {
            putBoolean(PreferencesKeys.keyMemoryAutoSave, newData.memoryAutoSave)
            putBoolean(PreferencesKeys.keyKeepLastRecord, newData.keepLastRecord)
            putBoolean(PreferencesKeys.keyAllowVibration, newData.allowVibration)
            putLong(PreferencesKeys.keyVibrationStrength, newData.vibrationStrength)
        }
        editor.apply()

        listener?.saveData(newData)
    }

    /** Сохраняет предпочтение */
    fun savePreference(key: String, variable: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, variable)
        editor.apply()
    }

}