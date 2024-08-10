package com.cobaltumapps.simplecalculator.v15.calculator.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression

class CalculatorLogger {
    fun logExpression(expression: Expression) {
        Log.i(TAG, "Было установлено новое выражение:\n ${expression.expression}")
    }

    fun logCalculateAction(result: String) {
        Log.i(TAG, "Был выполнен расчёт. Ответ: $result")
    }

    companion object {
        const val TAG = "CalculatorLoggerTag"
    }
}