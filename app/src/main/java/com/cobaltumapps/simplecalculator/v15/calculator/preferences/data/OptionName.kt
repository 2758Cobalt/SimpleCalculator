package com.cobaltumapps.simplecalculator.v15.calculator.preferences.data

import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys

enum class OptionName (keyOptionName: String) {
        AutoSaveMemory(PreferencesKeys.keyMemoryAutoSave),
        KeepLastRecord(PreferencesKeys.keyKeepLastRecord),
        AllowVibration(PreferencesKeys.keyAllowVibration)
    }