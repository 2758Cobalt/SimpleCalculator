package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

interface Preferences {
    fun getPreference(optionName: OptionName, conditionCallback: (condition: Boolean) -> Unit)
}