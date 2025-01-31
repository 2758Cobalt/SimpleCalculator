package com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces

interface CalculatorExpressionRemover {
    fun removeLastSymbolExpression(sourceString: String): String
    fun removeDigitsFromEnd(sourceString: String): String
}