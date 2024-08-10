package com.cobaltumapps.simplecalculator.v15.calculator.calculatorMonitor

import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode

interface CalculatorMonitorSender {
    fun sendCurrentExpression(expression: String)
    fun sendCalculationResult(result: String)
    fun sendCurrentAngleMode(angleMode: AngleMode)
}

interface CalculatorMemoryMonitor {

}