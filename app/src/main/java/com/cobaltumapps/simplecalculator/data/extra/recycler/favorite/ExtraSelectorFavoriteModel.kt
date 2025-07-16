package com.cobaltumapps.simplecalculator.data.extra.recycler.favorite

import com.cobaltumapps.simplecalculator.data.extra.enums.ExtraCalculatorType

data class ExtraSelectorFavoriteModel(
    val name: String,
    val calculatorId: String,
    val type: ExtraCalculatorType
)