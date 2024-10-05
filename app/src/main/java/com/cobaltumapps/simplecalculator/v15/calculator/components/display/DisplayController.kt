package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayControl
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

open class DisplayController(
    private var displayComponent: DisplayComponent
): DisplayControl {
    private var displayLogger = DisplayLogger()

    override fun setAngleField(angleMode: AngleMode) {
        displayComponent.setAngleField(angleMode)
        displayLogger.setAngleField(angleMode)
    }

    override fun setMemoryField(memoryValue: Number) {
        displayComponent.setMemoryField(memoryValue)
        displayLogger.setMemoryField(memoryValue)
    }

    override fun setExpressionField(newExpression: String) {
        displayComponent.setExpressionField(newExpression)
        displayLogger.setExpressionField(newExpression)
    }

    override fun setResultField(newResult: String) {
        displayComponent.setResultField(newResult)
        displayLogger.setResultField(newResult)
    }

    override fun clearExpressionField() {
        displayComponent.setExpressionField("")
        displayLogger.clearExpressionField()
    }

    // Очищает результат
    override fun clearResultField() {
        displayComponent.setResultField("")
        displayLogger.clearResultField()
    }

    fun allClearFields() {
        clearResultField()
        clearExpressionField()
    }
}

