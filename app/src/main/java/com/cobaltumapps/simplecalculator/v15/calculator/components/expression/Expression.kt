package com.cobaltumapps.simplecalculator.v15.calculator.components.expression

import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.interfaces.ExpressionBasicOperations

class Expression(userExpression: String = ""): ExpressionBasicOperations {
    private var expression: String = userExpression

    override fun setNewExpression(newExpression: String): String {
        expression = newExpression
        return expression
    }

    override fun getExpression(): String {
        return expression
    }

    override fun clearExpression() {
        expression = ""
    }

}