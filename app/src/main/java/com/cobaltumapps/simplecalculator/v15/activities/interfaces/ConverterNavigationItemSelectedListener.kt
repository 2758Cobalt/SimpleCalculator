package com.cobaltumapps.simplecalculator.v15.activities.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface ConverterNavigationItemSelectedListener {
    fun onConverterNavigationItemSelected(converterType: ConverterType = ConverterType.Weight)
}