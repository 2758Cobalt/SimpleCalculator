package com.cobaltumapps.simplecalculator.domain.calculator.parsing.interfaces

interface BaseExpressionParse {
    fun parse(sourceExpression: String): String
}