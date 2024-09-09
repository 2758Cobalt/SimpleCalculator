package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

open class DisplayControllerImpl: DisplayController {
    private var displayLogger = DisplayLogger()
    private var displayComponent: DisplayComponent? = null

    fun setNewDisplayAnimator(animator: DisplayAnimator) {
        displayComponent?.setNewDisplayAnimator(animator)
    }

    fun setNewDisplayInstance(instance: DisplayComponent) {
        displayComponent = instance
        displayLogger.newInstanceLog(instance)
    }

    override fun setAngleField(angleMode: AngleMode) {
        displayComponent?.setAngleField(angleMode)
        displayLogger.setAngleField(angleMode)
    }

    override fun setMemoryField(memoryValue: Number) {
        displayComponent?.setMemoryField(memoryValue)
        displayLogger.setMemoryField(memoryValue)
    }

    override fun setExpressionField(newExpression: String) {
        displayComponent?.setExpressionField(newExpression)
        displayLogger.setExpressionField(newExpression)
    }

    override fun setResultField(newResult: String) {
        displayComponent?.setResultField(newResult)
        displayLogger.setResultField(newResult)
    }

    override fun clearExpressionField() {
        displayComponent?.setExpressionField("")
        displayLogger.clearExpressionField()
    }

    // Очищает результат
    override fun clearResultField() {
        displayComponent?.setResultField("hidden")
        displayLogger.clearResultField()
    }

    fun allClearFields() {
        clearResultField()
        clearExpressionField()
    }
}

