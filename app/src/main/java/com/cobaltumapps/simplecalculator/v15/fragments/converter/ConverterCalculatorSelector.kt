package com.cobaltumapps.simplecalculator.v15.fragments.converter

import com.cobaltumapps.simplecalculator.v15.converter.adapters.OnAdapterSelectedItem

class ConverterCalculatorSelector: OnAdapterSelectedItem {
    private var selectedItemPosition = -1

    override fun selectedItemPosition(position: Int) {
        selectedItemPosition = position
    }

    fun getSelectedPosition(): Int {
        return selectedItemPosition
    }
}