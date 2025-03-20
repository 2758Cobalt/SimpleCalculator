package com.cobaltumapps.simplecalculator.v15.calculator.components.calculator

class BracketCloser {
    fun closeBracketExpression(sourceExpression: String): String {
        val builtExpression = StringBuilder(sourceExpression)
        var bracketOpenedCount = 0

        for (symbol in sourceExpression) {
            when(symbol) {
                '(' -> {
                    bracketOpenedCount += 1
                }
                ')' -> {
                    bracketOpenedCount -= 1
                }
            }
            println(bracketOpenedCount)
        }

        if (bracketOpenedCount > 0) {
            for (x in 1..bracketOpenedCount) {
                builtExpression.append(')')
            }
        }

        return builtExpression.toString()
    }
}