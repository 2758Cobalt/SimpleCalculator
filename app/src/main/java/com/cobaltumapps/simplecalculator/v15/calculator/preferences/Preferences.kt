package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName

interface Preferences {
    fun getPreferenceCondition(optionName: OptionName, conditionCallback: (condition: Boolean) -> Unit)
}