package com.cobaltumapps.simplecalculator.data.calculator.enums

import com.cobaltumapps.simplecalculator.v15.references.ConstantsCalculator

enum class KeyboardArifmeticOperation (val symbol: Char) {
    None(' '),
    Add(ConstantsCalculator.symbolAdd),
    Subtract(ConstantsCalculator.symbolSub),
    Multiply(ConstantsCalculator.symbolMul),
    Divide(ConstantsCalculator.symbolDiv),

    Point(ConstantsCalculator.symbolPoint),

    OpenBracket(ConstantsCalculator.symbolOpenBracket),
    CloseBracket(ConstantsCalculator.symbolCloseBracket)
}

