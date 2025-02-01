package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

class PreferencesManager(private val sharedPreferences: SharedPreferences): Preferences {
    override fun getPreference(optionName: OptionName, conditionCallback: (condition: Boolean) -> Unit) {
        conditionCallback.invoke(
            sharedPreferences.getBoolean(optionName.name, false)
        )
    }
}