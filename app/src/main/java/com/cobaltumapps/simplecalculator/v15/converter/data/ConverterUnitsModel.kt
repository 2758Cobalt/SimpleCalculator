package com.cobaltumapps.simplecalculator.v15.converter.data

/** Класс, который предоставляет адаптеру список названий и символов для каждого элемента конвертера */
data class ConverterUnitsModel(
    var unitsNameList: List<String> = listOf(),
    var unitsSpecialSymbolsList: List<String>? = null,
    var unitsValuesToConvertArray: Array<Array<Double>> = arrayOf(arrayOf())
)