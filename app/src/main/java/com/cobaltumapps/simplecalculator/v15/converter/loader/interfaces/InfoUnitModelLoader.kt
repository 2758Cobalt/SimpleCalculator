package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterUnitsModel
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface InfoUnitModelLoader {
    fun getUnitModel(converterType: ConverterType): ConverterUnitsModel
}