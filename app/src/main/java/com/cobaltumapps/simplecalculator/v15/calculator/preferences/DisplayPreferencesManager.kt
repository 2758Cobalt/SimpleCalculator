package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences

class DisplayPreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager(sharedPreferences) {
    fun getPreference(
        preferencesKey: String,
        resultCallback: (result: String?) -> Unit?
    ) {
        resultCallback.invoke(sharedPreferences.getString(preferencesKey, ""))
    }
}