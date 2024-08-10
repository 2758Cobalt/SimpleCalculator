package com.cobaltumapps.simplecalculator.v15.calculator.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorManagerListener

class CalculatorManagerLogger: CalculatorManagerListener {

    companion object {
        const val TAG = "CalculatorManagerLoggerTag"
    }

    override fun addToExpression(input: String) {
        Log.i(TAG, "Action: addition to an expression")
    }

    override fun getExpression(): String {
        Log.i(TAG, "Action: expression acquisition")
        return ""
    }

    override fun setExpression(newExpression: String) {
        Log.i(TAG, "Action: set new expression")
    }

    override fun clearExpression() {
        Log.i(TAG, "Action: expression cleaning")
    }

    override fun removeDigitsFromEnd(sourceString: String): String {
        Log.i(TAG, "Action: remove digits from end in expression")
        return sourceString
    }

    override fun setExpressionFromMemory(input: String) {
    }

    override fun removeLastSymbolExpression(sourceString: String): String {
        Log.i(TAG, "Action: removed last symbol in expression")
        return sourceString
    }

    override fun requestCalculateExpression(): Double {
        Log.i(TAG, "Action: calculation request")
        return -9.0
    }
}