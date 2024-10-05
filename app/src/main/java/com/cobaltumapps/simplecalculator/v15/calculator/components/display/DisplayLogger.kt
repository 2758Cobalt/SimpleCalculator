package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayViewerCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

class DisplayLogger:
    DisplayViewer,
    DisplayViewerCleaner,
    DisplayMemoryViewer,
    DisplayAngleViewer
{

    fun newInstanceLog(instance: DisplayComponent) {
        Log.i(TAG,"New component: $instance, has been connected")
    }

    override fun setAngleField(angleMode: AngleMode) {
        Log.i(TAG,"Angle mode changed: $angleMode")
    }

    override fun setMemoryField(memoryValue: Number) {
        Log.i(TAG,"A new value has been set in the memory field: " +
                "$memoryValue")
    }

    override fun setExpressionField(newExpression: String) {
        Log.i(TAG,"A new value has been set in the expression field: " +
                "$newExpression")
    }

    override fun setResultField(newResult: String) {
        Log.i(TAG,"A new value has been set in the result field: " +
                "$newResult")
    }

    override fun clearExpressionField() {
        Log.i(TAG,"Expression field has been cleared")
    }

    override fun clearResultField() {
        Log.i(TAG,"Result field has been cleared")
    }

    companion object {
        const val TAG = "DisplayLoggerTag"
    }

}