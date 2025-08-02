package com.cobaltumapps.simplecalculator.domain.calculator.expression

interface ExpressionCloser {
    fun closeExpression(sourceExpression: String): String
}