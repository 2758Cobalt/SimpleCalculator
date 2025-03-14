package com.cobaltumapps.simplecalculator.v15.converter.data

/** Класс предоставляющий для каждого тип конвертера информацию в виде, названия, иконки, типа и списка моделей самих конвертеров */
data class ConverterData(
    var converterPageData: ConverterPageData = ConverterPageData(),
    var converterUnitsModel: ConverterUnitsModel = ConverterUnitsModel()
)