package com.cobaltumapps.simplecalculator.domain.calculator.rules

import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.push
import com.cobaltumapps.simplecalculator.domain.calculator.rules.interfaces.ExpressionRule
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator
import java.math.BigDecimal

class UnaryMinusRule: ExpressionRule {
    override fun apply(
        index: Int,
        expression: String,
        currentNumber: StringBuilder,
        stackOperands: ArrayDeque<BigDecimal>,
        stackOperators: ArrayDeque<Char>
    ): Boolean {
        if (index + 1 >= expression.length) return false

        val postIndexExpression = expression[index + 1]
        val rule = postIndexExpression == ConstantsBaseCalculator.symbolSub

        if (rule) {
            stackOperands.push(BigDecimal.ZERO)
        }

        return rule
    }
}