package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardControllerMaster
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

class NumpadLogger: KeyboardControllerMaster(), KeyboardNumpadListener {
    override fun onClickNumber(number: Number) {
        Log.i(LOG_TAG, "onClickNumber: $number")
    }

    override fun onClickMathOperation(operation: MathOperation) {
        Log.i(LOG_TAG, "onClickOperation: ${operation.symbol}")
    }

    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        Log.i(LOG_TAG, "onClickSpecialOperation: ${operation.symbol}")
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        Log.i(LOG_TAG, "onClickSpecialFunction: ${function.name}")
    }

    companion object {
        const val LOG_TAG = "NumpadControllerLogTag"
    }
}