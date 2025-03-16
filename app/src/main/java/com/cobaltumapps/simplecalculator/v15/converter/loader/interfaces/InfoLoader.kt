package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterLoaderData
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface InfoLoader {
    fun getConverterData(converterType: ConverterType): ConverterLoaderData
}