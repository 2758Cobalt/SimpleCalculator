package com.cobaltumapps.simplecalculator.v15.calculator.components.calculator

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces.CalculatorControllerCalculation
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces.CalculatorControllerOperations
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.logger.CalculatorControllerCalculationLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.managers.ExpressionRemover
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore

/* Реализация контроллера калькулятора */
class CalculatorController(private var calculatorInstance: CalculatorCore? = null): CalculatorControllerCalculation,
    CalculatorControllerOperations {
    private var currentAngleMode = AngleMode.RAD
    private var calculatorControllerLogger = CalculatorControllerCalculationLogger()

    private val calculatorExpressionRemover = ExpressionRemover()
    private var userExpression = Expression()

    init {
        if (calculatorInstance == null) {
            Log.e(LOG_TAG, "!!! Calculator core instance is null !!!")
        }
    }

    // Добавляет к текущему выражению новый символ (элемент)
     override fun addToExpression(newElement: String): String {
        userExpression.setNewExpression(userExpression.getExpression() + newElement)
        updateCalculatorExpression()
        return userExpression.getExpression()
    }

    fun setExpression(newExpression: String) {
        userExpression.setNewExpression(newExpression)
        updateCalculatorExpression()
    }

    // Возвращает текущее выражение
    override fun getUserExpression(): Expression {
        return userExpression
    }

    // Очищает полностью выражение
    override fun clearExpression() {
        userExpression.clearExpression()
        updateCalculatorExpression()
    }

    // Удаляет последний символ в выражении
    override fun removeLastSymbolExpression() {
        userExpression.setNewExpression(
            calculatorExpressionRemover.removeLastSymbolExpression(userExpression.getExpression())
        )
        calculatorControllerLogger.removeLastSymbolExpression()
    }

    override fun removeDigitsGroup() {
        userExpression.setNewExpression(
            calculatorExpressionRemover.removeDigitsFromEnd(
                userExpression.getExpression()
            )
        )
        calculatorControllerLogger.removeDigitsGroup()
    }

    private fun updateCalculatorExpression() {
        calculatorInstance?.setNewExpression(userExpression)
    }

    override fun calculateExpression(): String {
        bracketsCloser(getUserExpression().getExpression())
        calculatorInstance?.calculate(false)
        return getCalculatedExpression().toString()
    }

    override fun getCalculatedExpression(): Double {
        return calculatorInstance?.result!!
    }

    fun setSwitchAngleMode(): AngleMode {
        currentAngleMode = when(currentAngleMode) {
            AngleMode.RAD -> AngleMode.DEG
            AngleMode.DEG -> AngleMode.RAD
        }
        calculatorInstance?.setNewAngleMode(currentAngleMode)
        return currentAngleMode
    }

    fun closeExpressionString(): String {
        val sourceExpression = calculatorInstance?.closeExpressionString(getUserExpression().getExpression())!!
        userExpression.setNewExpression(sourceExpression)
        calculatorInstance?.setNewExpression(getUserExpression())
        return sourceExpression
    }

    // Закрывает большное количество скобок (если нужно)
    fun bracketsCloser(expression: String): String {
        val builtExpression = StringBuilder(expression)
        var bracketOpenedCount = 0

        for (symbol in expression) {
            when(symbol) {
                '(' -> {
                    bracketOpenedCount += 1
                }
                ')' -> {
                    bracketOpenedCount -= 1
                }
            }
            println(bracketOpenedCount)
        }

        if (bracketOpenedCount > 0) {
            for (x in 1..bracketOpenedCount) {
                builtExpression.append(')')
            }
        }

        userExpression.setNewExpression(builtExpression.toString())
        return builtExpression.toString()
    }

    companion object {
        const val LOG_TAG = "SC_CalculatorControllerTag"
    }
}

