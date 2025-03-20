package com.cobaltumapps.simplecalculator.v15.calculator.preferences.data

import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys

enum class OptionName (keyOptionName: String) {
        AutoSaveMemory(PreferencesKeys.KEY_AUTOSAVE_MEMORY),
        KeepLastRecord(PreferencesKeys.KEY_KEEP_LAST_RECORD),
        AllowVibration(PreferencesKeys.KEY_ALLOW_VIBRATION)
    }