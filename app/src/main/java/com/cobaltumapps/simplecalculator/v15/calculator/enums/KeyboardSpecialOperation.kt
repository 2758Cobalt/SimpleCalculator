package com.cobaltumapps.simplecalculator.v15.calculator.enums

import com.cobaltumapps.simplecalculator.v15.references.ConstantsCalculator

enum class KeyboardSpecialOperation(val symbol: String) {
    SkipOperation(" "),

    Point(ConstantsCalculator.symbolPoint.toString()),
    Percent(ConstantsCalculator.symbolPercent.toString()),
    Power(ConstantsCalculator.symbolPower.toString()),
    SQRT(ConstantsCalculator.symbolSqrt.toString()),
    Factorial(ConstantsCalculator.symbolFactorial.toString()),

    PI(ConstantsCalculator.symbolPI.toString()),
    Exp(ConstantsCalculator.symbolExp.toString()),

    Sinus("sin("),
    Cosine("cos("),
    Tangent("tan("),
    Cotangent("cot("),

    Log("log("),
    Ln("ln("),
}

