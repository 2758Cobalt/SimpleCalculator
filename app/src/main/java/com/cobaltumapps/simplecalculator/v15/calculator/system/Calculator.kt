package com.cobaltumapps.simplecalculator.v15.calculator.system

import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.loggers.CalculatorLogger
import com.cobaltumapps.simplecalculator.v15.calculator.managers.CalculatorManager
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression

open class Calculator {
    // Входные данные
    protected var userExpression: Expression? = null
    var trimRange: Int = 9
    var angleMode: AngleMode = AngleMode.Radian

    // Экземпляры
    var calculatorManagerInstance: CalculatorManager? = null
    protected var calculatorLogger = CalculatorLogger()

    // Обищие данные
    var result: Double = 0.0

    fun setNewExpression(expression: Expression) {
        this.userExpression = expression
        calculatorLogger.logExpression(expression)
    }

    // Расчитывает выражение
    open fun calculate(canTrimExpression: Boolean = false){
        result = 0.05
        calculatorLogger.logCalculateAction(result.toString())
    }
}

