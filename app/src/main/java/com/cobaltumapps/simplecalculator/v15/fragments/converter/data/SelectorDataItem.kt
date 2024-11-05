package com.cobaltumapps.simplecalculator.v15.fragments.converter.data

import com.cobaltumapps.simplecalculator.v15.fragments.converter.enums.ConverterUnit

data class SelectorDataItem(
    var titleResId: Int,
    var drawableResId: Int,
    var unitType: ConverterUnit = ConverterUnit.None
)