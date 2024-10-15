package com.cobaltumapps.simplecalculator.v15.calculator.components.display

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.DisplayControl
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.ValueTranslator

open class DisplayController(
    private var displayComponent: DisplayComponent
): DisplayControl {
    private var displayLogger = DisplayLogger()
    private val displayValueTranslator = ValueTranslator()

    // Logic
    private var canAnimateResult = false

    // Установка значения типа угла
    override fun setAngleField(angleMode: AngleMode) {
        displayComponent.setAngleField(angleMode)
        displayLogger.setAngleField(angleMode)
    }

    // Установка значения в поле памяти
    override fun setMemoryField(memoryValue: Number) {
        displayComponent.setMemoryField(memoryValue)
        displayLogger.setMemoryField(memoryValue)
    }

    // Установка значения в поле выражения
    override fun setExpressionField(newExpression: String) {
        displayComponent.setExpressionField(newExpression)
        if (canAnimateResult) {
            displayComponent.displayAnimator.playHiddenResultAnim()
            canAnimateResult = false
        }
        displayLogger.setExpressionField(newExpression)
    }

    // Установка значение в поле результата
    override fun setResultField(newResult: String) {
        displayComponent.setResultField(
            displayValueTranslator.translate(newResult.toDouble())
        )

        canAnimateResult = true
        displayComponent.displayAnimator.playDisplayResultAnim()
        displayLogger.setResultField(newResult)
    }

    // Очистка выражения
    override fun clearExpressionField() {
        displayComponent.setExpressionField("")
        displayLogger.clearExpressionField()
    }

    // Очистка результата
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