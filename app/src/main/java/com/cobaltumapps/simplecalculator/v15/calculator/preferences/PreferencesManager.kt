package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.Context
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesController
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet.PreferencesSideSheet

class PreferencesManager
    (
    context: Context
): PreferencesController {
    private val sideSheetDialog = PreferencesSideSheet(context)


    fun openPreferencesService() {
        sideSheetDialog.show()
    }

    override fun onChangePreference() {
    }

    override fun onResetPreferences() {
    }

    override fun onSavePreferences() {
    }
}

