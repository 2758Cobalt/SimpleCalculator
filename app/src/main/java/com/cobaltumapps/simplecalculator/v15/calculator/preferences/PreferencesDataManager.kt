package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesDataManagerListener

/** Менеджер, отвечающий за загрузку и сохранение предпочтений.*/
class PreferencesDataManager(
    private val sharedPreferences: SharedPreferences,
    private val listener: PreferencesDataManagerListener? = null
): PreferencesDataManagerListener {
    private val prefLoader = PreferencesLoader(sharedPreferences, listener)
    private val prefSaver = PreferencesSaver(sharedPreferences, listener)

    override fun loadData(): PreferencesUserData {
        return prefLoader.loadData()
    }

    override fun saveData(newData: PreferencesUserData): PreferencesUserData {
        return prefSaver.saveData(newData)
    }
}

