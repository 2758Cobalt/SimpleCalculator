package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayExpressionControl
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.ValueTranslator

open class DisplayExpressionController(
    private var displayComponent: DisplayComponent
): DisplayExpressionControl {
    private var displayLogger = DisplayExpressionLogger()
    private val displayValueTranslator = ValueTranslator()

    private var canAnimateResult = false

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
        if (canAnimateResult) {
            displayComponent.displayAnimator.playHiddenResultAnim()
            canAnimateResult = false
        }
        displayLogger.setExpressionField(newExpression)
    }

    override fun setResultField(newResult: String) {
        displayComponent.setResultField(displayValueTranslator.translate(newResult))

        if (!canAnimateResult) {
            canAnimateResult = true
            displayComponent.displayAnimator.playDisplayResultAnim()
        }
            displayLogger.setResultField(newResult)
    }

    override fun clearExpressionField() {
        displayComponent.setExpressionField("")
        displayLogger.clearExpressionField()
    }

    override fun clearResultField() {
        displayComponent.displayAnimator.playHiddenResultAnim {
            displayComponent.setResultField("")
        }
        displayLogger.clearResultField()
    }

    fun allClearFields() {
        canAnimateResult = false
        clearResultField()
        clearExpressionField()
    }
}