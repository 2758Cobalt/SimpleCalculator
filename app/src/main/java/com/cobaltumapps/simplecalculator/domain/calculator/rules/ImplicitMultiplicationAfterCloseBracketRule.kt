package com.cobaltumapps.simplecalculator.domain.calculator.rules

import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator.Companion.push
import com.cobaltumapps.simplecalculator.domain.calculator.rules.interfaces.ExpressionRule
import com.cobaltumapps.simplecalculator.v15.references.ConstantsBaseCalculator
import java.math.BigDecimal

class ImplicitMultiplicationAfterCloseBracketRule: ExpressionRule {
    override fun apply(
        index: Int,
        expression: String,
        currentNumber: StringBuilder,
        stackOperands: ArrayDeque<BigDecimal>,
        stackOperators: ArrayDeque<Char>
    ): Boolean {
        if (index <= 0 || index + 1 >= expression.length) return false

        val preIndexExpression = expression[index - 1]
        val rule = preIndexExpression.isDigit()

        if (rule) {
            stackOperators.push(ConstantsBaseCalculator.symbolMul)
            if (currentNumber.isNotEmpty()) {
                stackOperands.push(BigDecimal(currentNumber.toString()))
                currentNumber.clear()
            }
        }

        return rule
    }
}