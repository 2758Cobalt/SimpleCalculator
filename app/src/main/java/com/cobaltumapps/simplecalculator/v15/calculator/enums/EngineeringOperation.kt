package com.cobaltumapps.simplecalculator.v15.calculator.enums

import com.cobaltumapps.simplecalculator.references.ConstantsCalculator

enum class EngineeringOperation (val symbol: Char) {
    None(' '),
    Add(ConstantsCalculator.symbolAdd),
    Subtract(ConstantsCalculator.symbolSub),
    Multiply(ConstantsCalculator.symbolMul),
    Divide(ConstantsCalculator.symbolDiv),

    Point(ConstantsCalculator.symbolPoint),
    Percent(ConstantsCalculator.symbolPercent),

    OpenBracket(ConstantsCalculator.symbolOpenBracket),
    CloseBracket(ConstantsCalculator.symbolCloseBracket)
}

