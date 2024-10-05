package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayControl
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.ValueTranslator

open class DisplayController(
    private var displayComponent: DisplayComponent
): DisplayControl {
    private var displayLogger = DisplayLogger()
    private val displayValueTranslator = ValueTranslator()

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
        displayComponent.setResultField(
            displayValueTranslator.translate(newResult.toDouble())
        )
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

