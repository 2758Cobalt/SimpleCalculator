package com.cobaltumapps.simplecalculator.v15.builders

import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression

// Отвечает за постройку выражения
class ExpressionBuilder {

    fun buildExpression(expression: String, input: String): Expression {
        return Expression(expression + input)
    }

}