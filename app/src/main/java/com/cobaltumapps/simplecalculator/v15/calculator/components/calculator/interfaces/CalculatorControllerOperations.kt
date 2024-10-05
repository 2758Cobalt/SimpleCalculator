package com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression

interface CalculatorControllerOperations {
    fun addToExpression(newElement: String): String
    fun getUserExpression(): Expression
    fun removeLastSymbolExpression()
    fun removeDigitsGroup()
    fun clearExpression()
}