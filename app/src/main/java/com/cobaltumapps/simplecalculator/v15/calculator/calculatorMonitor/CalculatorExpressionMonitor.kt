package com.cobaltumapps.simplecalculator.v15.calculator.calculatorMonitor

import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayExpressionFieldManager
import com.cobaltumapps.simplecalculator.v15.calculator.display.interfaces.DisplayManager

// Класс, отвечающий за передачу нужных данных дисплею для отображения
class CalculatorExpressionMonitor: DisplayExpressionFieldManager {
    private var displayListener: DisplayExpressionFieldManager? = null

    fun setDisplayListener(listener: DisplayManager) {
        displayListener = listener
    }

    override fun setExpressionField(newExpression: String) {
        displayListener?.setExpressionField(newExpression)
    }

    override fun setCalculatedExpression(result: String) {
        displayListener?.setCalculatedExpression(result)
    }
}

