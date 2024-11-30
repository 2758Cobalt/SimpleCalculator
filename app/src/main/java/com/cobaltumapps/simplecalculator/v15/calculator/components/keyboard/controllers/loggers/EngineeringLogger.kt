package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.loggers

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialOperationListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation

class EngineeringLogger : KeyboardSpecialFunctionsListener, KeyboardSpecialOperationListener {
    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        Log.i(LOG_TAG, "onClickSpecialOperation: $operation")
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        Log.i(LOG_TAG, "onClickSpecialFunction: $function")
    }

    companion object {
        const val LOG_TAG = "EngineeringControllerLogTag"
    }
}