package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayExpressionViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayViewerCleaner

open class DisplayComponent:
    Fragment(),
    DisplayExpressionViewer,
    DisplayViewerCleaner,
    DisplayMemoryViewer,
    DisplayAngleViewer
{
    var displayController: DisplayExpressionController = DisplayExpressionController(this)
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