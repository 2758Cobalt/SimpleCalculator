package com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MemoryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.TrigonometryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.EngineeringNumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.loggers.InputControllerLogger

class EngineeringInputControllerLogger: InputControllerLogger(), EngineeringNumpadKeyboard {
    override fun onClickOperation(operation: MathOperation) {
        Log.i(TAG, "Handled onClickOperation. Operation: $operation")
    }


    override fun onClickMemoryOperation(memoryOperation: MemoryOperation) {
        Log.i(TAG, "Handled onClickMemoryOperation. Operation: $memoryOperation")
    }

    override fun onClickTrigonometryOperation(trigonometryOperation: TrigonometryOperation) {
        Log.i(TAG, "Handled onClickTrigonometryOperation. Operation: ")
    }

    override fun onClickAngleMode(angleMode: AngleMode) {
    }
}