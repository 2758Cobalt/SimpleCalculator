package com.cobaltumapps.simplecalculator.v15.calculator.components.settings

interface Settings {
    fun getPreferenceCondition(keyName: String, defaultValue: Boolean = false): Boolean
    fun setPreferenceCondition(keyName: String, value: Boolean)
}