package com.cobaltumapps.simplecalculator.models

import com.cobaltumapps.simplecalculator.enums.ConverterTypes

data class ConverterDataModel(
    var title: String = "Nonverter",
    var converterType: ConverterTypes = ConverterTypes.Any,
    var thumbnailResourceId: Int? = null,
    var converterUnitsNamesList: MutableList<String> = mutableListOf(),
    var convertersSpecialSymbolsList: List<String> = listOf(),
    var convertersValuesToConvert:  Array<Array<Double>> = arrayOf(arrayOf())
)
