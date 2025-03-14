package com.cobaltumapps.simplecalculator.v15.converter.loader.interfaces

import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterType

interface InfoTitleLoader {
    fun getTitle(converterType: ConverterType): String
}