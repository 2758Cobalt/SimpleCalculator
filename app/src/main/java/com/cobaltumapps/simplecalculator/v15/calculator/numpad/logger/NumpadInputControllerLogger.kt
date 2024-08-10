package com.cobaltumapps.simplecalculator.v15.calculator.numpad.logger

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.enums.ActionOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.NumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.loggers.InputControllerLogger

class NumpadInputControllerLogger: InputControllerLogger(), NumpadListener {

    override fun onClickNumber(number: Number) {
        Log.i(TAG, "Handled onClickNumber. Number: $number")
    }

    override fun onClickOperation(operation: MathOperation) {
        Log.i(TAG, "Handled onClickOperation. Operation: $operation")
    }

    override fun onClickAction(action: ActionOperation) {
        Log.i(TAG, "Handled onClickAction. Action operation: $action")
    }

    override fun onActionAllClear() {
        Log.w(TAG, "Action performed: Cleaning")
    }

    override fun onActionEqual() {
        Log.w(TAG, "Action performed: Equal")
    }

    override fun onActionBackspace() {
        Log.w(TAG, "Action performed: Backspace")
    }

    override fun onActionGroupClean() {
        Log.w(TAG, "Action performed: Group cleaning")
    }
}