package com.cobaltumapps.simplecalculator.domain.calculator.enums

import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator

enum class BaseCalculatorSymbol(val symbol: Char) {
    ADD(ConstantsBaseCalculator.symbolAdd),
    SUBTRACT(ConstantsBaseCalculator.symbolSub),
    MULTIPLY(ConstantsBaseCalculator.symbolMul),
    DIVIDE(ConstantsBaseCalculator.symbolDiv),

    POWER(ConstantsBaseCalculator.symbolPower),
    SQRT(ConstantsBaseCalculator.symbolSqrt),
    PERCENT(ConstantsBaseCalculator.symbolPercent),
    FACTORIAL(ConstantsBaseCalculator.symbolFactorial),
    OPEN_BRACKET(ConstantsBaseCalculator.symbolOpenBracket),
    CLOSE_BRACKET(ConstantsBaseCalculator.symbolCloseBracket),
    POINT(ConstantsBaseCalculator.symbolPoint),
    EXPONENT(ConstantsBaseCalculator.symbolExponent),
    PI(ConstantsBaseCalculator.symbolPI)
}