package com.cobaltumapps.simplecalculator.v15.calculator.enums

import com.cobaltumapps.simplecalculator.references.ConstantsCalculator

enum class MathOperation (val symbol: Char) {
    None(' '),
    Add(ConstantsCalculator.symbolAdd),
    Subtract(ConstantsCalculator.symbolSub),
    Multiply(ConstantsCalculator.symbolMul),
    Divide(ConstantsCalculator.symbolDiv),

    Point(ConstantsCalculator.symbolPoint),

    OpenBracket(ConstantsCalculator.symbolOpenBracket),
    CloseBracket(ConstantsCalculator.symbolCloseBracket)
}

