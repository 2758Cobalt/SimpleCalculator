package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.SharedPreferences
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesManagerObserver

open class PreferencesManager(val sharedPreferences: SharedPreferences):
    PreferencesManagerObserver {
    override fun getPreferenceCondition(
        optionName: OptionName,
        conditionCallback: (condition: Boolean) -> Unit?
    ): Boolean {
        val gotCondition = sharedPreferences.getBoolean(optionName.name, false)
        conditionCallback.invoke(gotCondition)
        return gotCondition
    }
}