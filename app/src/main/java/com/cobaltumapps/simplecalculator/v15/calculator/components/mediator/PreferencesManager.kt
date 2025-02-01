package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.activities.SettingsActivity

class PreferencesManager(private val sharedPreferences: SharedPreferences): Preferences {
    override fun getPreference(optionName: SettingsActivity.OptionName, conditionCallback: (condition: Boolean) -> Unit?) {
        conditionCallback.invoke(
            sharedPreferences.getBoolean(optionName.name, false)
        )
    }
}