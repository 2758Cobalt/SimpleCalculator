package com.cobaltumapps.simplecalculator.v15.calculator.components.expression.interfaces

interface ExpressionBasicOperations {
    fun setNewExpression(newExpression: String): String
    fun getExpression(): String
    fun clearExpression()
}