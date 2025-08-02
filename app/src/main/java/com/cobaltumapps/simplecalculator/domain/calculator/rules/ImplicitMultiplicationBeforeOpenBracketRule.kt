package com.cobaltumapps.simplecalculator.domain.calculator.rules

import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.push
import com.cobaltumapps.simplecalculator.domain.calculator.rules.interfaces.ExpressionRule
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator
import java.math.BigDecimal

class ImplicitMultiplicationBeforeOpenBracketRule: ExpressionRule {
    override fun apply(
        index: Int,
        expression: String,
        currentNumber: StringBuilder,
        stackOperands: ArrayDeque<BigDecimal>,
        stackOperators: ArrayDeque<Char>
    ): Boolean {
        if (index <= 0) return false

        val preIndexExpression = expression[index - 1]
        val rule = preIndexExpression.isDigit() || preIndexExpression == ConstantsBaseCalculator.symbolPoint

        if (rule) {
            stackOperators.push(ConstantsBaseCalculator.symbolMul)

            if (currentNumber.isNotEmpty()) {
                stackOperands.push(BigDecimal(currentNumber.toString()))
                currentNumber.clear()
            }

            if (preIndexExpression == ConstantsBaseCalculator.symbolPoint) {
                currentNumber.append('0')
            }
        }

        return rule
    }
}