package com.cobaltumapps.simplecalculator.domain.calculator.rules.interfaces

import java.math.BigDecimal

interface ExpressionRule {
    fun apply(
        index: Int,
        expression: String,
        currentNumber: StringBuilder,
        stackOperands: ArrayDeque<BigDecimal>,
        stackOperators: ArrayDeque<Char>
    ): Boolean
}