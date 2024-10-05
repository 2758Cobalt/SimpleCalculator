package com.cobaltumapps.simplecalculator.v15.calculator.preferences.sidesheet

import android.content.Context
import com.cobaltumapps.simplecalculator.databinding.SideSheetPreferencesBinding
import com.google.android.material.sidesheet.SideSheetDialog

class PreferencesSideSheet(context: Context) : SideSheetDialog(context) {
    private val binding by lazy { SideSheetPreferencesBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
    }
}