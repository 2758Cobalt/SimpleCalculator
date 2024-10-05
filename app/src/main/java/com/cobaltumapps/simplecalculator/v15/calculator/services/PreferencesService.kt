package com.cobaltumapps.simplecalculator.v15.calculator.services

import android.content.Context
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.google.android.material.sidesheet.SideSheetDialog

class PreferencesService(
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

interface PreferencesController {
    fun onChangePreference()
    fun onResetPreferences()
    fun onSavePreferences()
}

class PreferencesSideSheet(context: Context) : SideSheetDialog(context) {
    private val binding by lazy { SideSheetPreferencesBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
    }
}