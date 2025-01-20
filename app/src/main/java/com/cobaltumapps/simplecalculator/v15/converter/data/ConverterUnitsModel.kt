package com.cobaltumapps.simplecalculator.v15.converter.data

/** Класс, который предоставляет адаптеру список названий и символов для каждого элемента конвертера */
data class ConverterUnitsModel(
    var unitsNameList: List<String> = listOf(),
    var unitsSpecialSymbolsList: List<String> = listOf()
)

data class ConverterUnitModel(
    var unitName: String,
    var unitSpecialSymbol: String
)