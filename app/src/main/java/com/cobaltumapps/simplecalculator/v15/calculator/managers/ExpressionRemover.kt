package com.cobaltumapps.simplecalculator.v15.calculator.managers

import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover

class ExpressionRemover: CalculatorExpressionRemover {
    override fun removeLastSymbolExpression(sourceString: String): String {
        val stringBuilder = StringBuilder(sourceString)

        stringBuilder.deleteCharAt(stringBuilder.lastIndex)

        return stringBuilder.toString()
    }

    override fun removeDigitsFromEnd(sourceString: String): String {
        val stringBuilder = StringBuilder(sourceString)
        if (sourceString.isNotEmpty()) {
            var index = stringBuilder.length - 1

            if (!stringBuilder[index].isDigit()) {
                stringBuilder.deleteCharAt(index)
            } else {
                // Находим первый символ, который не является цифрой
                while (index >= 0 && stringBuilder[index].isDigit()) {
                    stringBuilder.deleteCharAt(index)
                    index--
                }
            }
        }

        return stringBuilder.toString()
    }
}