package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface InfoUnitValuesLoader {
    fun getValuesToConvert(converterType: ConverterType): Array<Array<Double>>
}