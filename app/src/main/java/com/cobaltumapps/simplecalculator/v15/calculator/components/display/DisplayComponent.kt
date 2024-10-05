package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayViewerCleaner
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

open class DisplayComponent:
    Fragment(),
    DisplayViewer,
    DisplayViewerCleaner,
    DisplayMemoryViewer,
    DisplayAngleViewer
{
    var displayController: DisplayController = DisplayController(this)
    var displayAnimator: DisplayAnimator = DisplayAnimator()

    override fun setAngleField(angleMode: AngleMode) {
        displayController.setAngleField(angleMode)
    }

    override fun setMemoryField(memoryValue: Number) {
        displayController.setMemoryField(memoryValue)
    }

    override fun setExpressionField(newExpression: String) {
        displayController.setExpressionField(newExpression)
    }

    override fun setResultField(newResult: String) {
        displayController.setResultField(newResult)
    }

    override fun clearExpressionField() {
        displayController.clearExpressionField()
    }

    override fun clearResultField() {
        displayController.clearResultField()
    }
}