package com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

interface PreferencesManagerObserver {
    fun getPreferenceCondition(optionName: OptionName, conditionCallback: (condition: Boolean) -> Unit?): Boolean
}