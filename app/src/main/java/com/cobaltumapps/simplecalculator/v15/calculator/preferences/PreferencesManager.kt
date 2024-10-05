package com.cobaltumapps.simplecalculator.v15.calculator.preferences

import android.content.Context
import android.view.View
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesController
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet.PreferencesSideSheet
import com.google.android.material.sidesheet.SideSheetCallback

class PreferencesManager(context: Context): SideSheetCallback() {
    private val sideSheetDialog = PreferencesSideSheet(context)
    var preferenceListener: PreferencesController? = null

    fun openPreferencesService() {
        sideSheetDialog.show()
    }

    override fun onStateChanged(sheet: View, newState: Int) {
    }

    override fun onSlide(sheet: View, slideOffset: Float) {
    }
}

