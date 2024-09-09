package com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.logger

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorControllerOperations
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression

class CalculatorControllerLogger: CalculatorController, CalculatorControllerOperations {
    companion object {
        const val LOG_TAG = "SC_CalcControllerTag"
    }

    override fun calculateExpression(): String {
        Log.d(LOG_TAG, "Calculate expression")
        return ""
    }

    override fun addToExpression(newElement: String): String {
        Log.d(LOG_TAG, "$newElement added to expression")
        return ""
    }

    override fun getUserExpression(): Expression {
        Log.d(LOG_TAG, "gotten user expression")
        return Expression()
    }

    override fun getCalculatedExpression(): Double {
        Log.d(LOG_TAG, "gotten calculated expression")
        return 0.0
    }

    override fun removeLastSymbolExpression() {
        Log.d(LOG_TAG, "From the expression removed last symbol ")
    }

    override fun removeDigitsGroup() {
        Log.d(LOG_TAG, "From the expression removed group the digits")
    }

    override fun clearExpression() {
        Log.d(LOG_TAG, "The expression is cleared")
    }
}