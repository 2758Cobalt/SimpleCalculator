package com.cobaltumapps.simplecalculator.domain.calculator.enums

import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator

enum class BaseCalculatorTrigonometryString(val symbol: String) {
    SINE(ConstantsBaseCalculator.sinSymbol),
    COSINE(ConstantsBaseCalculator.cosSymbol),
    TANGENT(ConstantsBaseCalculator.tanSymbol),
    COTANGENT(ConstantsBaseCalculator.cotSymbol),

    LOG(ConstantsBaseCalculator.logSymbol),
    LN(ConstantsBaseCalculator.lnSymbol)
}