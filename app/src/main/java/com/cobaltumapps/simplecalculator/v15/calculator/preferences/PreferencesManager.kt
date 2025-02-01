package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

class PreferencesManager(val sharedPreferences: SharedPreferences): Preferences {
    override fun getPreferenceCondition(optionName: OptionName, conditionCallback: (condition: Boolean) -> Unit) {
        conditionCallback.invoke(
            sharedPreferences.getBoolean(optionName.name, false)
        )
    }
}