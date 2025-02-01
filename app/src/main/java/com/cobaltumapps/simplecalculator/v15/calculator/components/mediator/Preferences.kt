package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import com.cobaltumapps.simplecalculator.v15.activities.SettingsActivity

interface Preferences {
    fun getPreference(optionName: SettingsActivity.OptionName, conditionCallback: (condition: Boolean) -> Unit?)
}