package com.cobaltumapps.simplecalculator.v15.converter.data

import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterUnit

data class SelectorDataItem(
    var titleResId: Int,
    var drawableResId: Int,
    var unitType: ConverterUnit = ConverterUnit.None
)