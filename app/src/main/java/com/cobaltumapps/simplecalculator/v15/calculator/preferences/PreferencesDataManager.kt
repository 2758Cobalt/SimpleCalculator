package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesDataManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesLoaderListener
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesSaverListener

/** Менеджер, отвечающий за загрузку и сохранение предпочтений.*/
class PreferencesDataManager(
    private val sharedPreferences: SharedPreferences
): PreferencesDataManagerListener {

    // Слушатели PreferencesLoader и PreferencesSaver
    var loaderListener: PreferencesLoaderListener? = null
    var saverListener: PreferencesSaverListener? = null

    private val prefLoader = PreferencesLoader(sharedPreferences, loaderListener)
    private val prefSaver = PreferencesSaver(sharedPreferences, saverListener)

    override fun loadData(): PreferencesUserData {
        return prefLoader.loadData()
    }

    override fun saveData(newData: PreferencesUserData) {
        prefSaver.saveData(newData)
    }
}