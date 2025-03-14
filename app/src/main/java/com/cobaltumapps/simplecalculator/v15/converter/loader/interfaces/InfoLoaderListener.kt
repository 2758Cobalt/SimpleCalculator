package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.data.ConverterData

interface InfoLoaderListener {
    fun updateConverterData(converterData: ConverterData)
}