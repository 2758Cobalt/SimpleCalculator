package com.cobaltumapps.simplecalculator.v15.calculator.managers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover

class ExpressionRemover: CalculatorExpressionRemover {
    override fun removeLastSymbolExpression(sourceString: String): String {
        return if (sourceString.isNotEmpty()) {
                val stringBuilder = StringBuilder(sourceString)

                stringBuilder.deleteCharAt(stringBuilder.lastIndex)

                stringBuilder.toString()
            }
            else {
                Log.e(LOG_TAG, "removeLastSymbolExpression: sourceString is blank or null")
                return ""
        }
    }

    override fun removeDigitsFromEnd(sourceString: String): String {
        return if (sourceString.isNotEmpty()) {
            val stringBuilder = StringBuilder(sourceString)
            stringBuilder.apply {
                var index = length - 1

                if (!stringBuilder[index].isDigit()) {
                    deleteCharAt(index)
                } else {
                    // Находим первый символ, который не является цифрой
                    while (index >= 0 && stringBuilder[index].isDigit()) {
                        deleteCharAt(index)
                        index--
                    }
                }
            }

            stringBuilder.toString()
        }

        else {
            Log.e(LOG_TAG, "removeDigitsFromEnd: sourceString is blank or null")
            return ""
        }
    }

    companion object {
        const val LOG_TAG = ""
    }
}