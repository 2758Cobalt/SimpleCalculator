package com.cobaltumapps.simplecalculator.domain.calculator.managers

import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.BracketCloser
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces.CalculatorControllerCalculation
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces.CalculatorControllerOperations
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.managers.ExpressionRemover
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore

class CalculatorController(private var calculatorInstance: CalculatorCore): CalculatorControllerCalculation,
    CalculatorControllerOperations {
    private var currentAngleMode = AngleMode.RAD

    private val calculatorExpressionRemover = ExpressionRemover()
    private var userExpression = Expression()

    private val bracketCloser = BracketCloser()

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

    fun setExpression(newExpression: Number) {
        userExpression.setNewExpression(newExpression.toString())
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
    }

    override fun removeDigitsGroup() {
        userExpression.setNewExpression(
            calculatorExpressionRemover.removeDigitsFromEnd(
                userExpression.getExpression()
            )
        )
    }

    private fun updateCalculatorExpression() {
        calculatorInstance.setNewExpression(userExpression)
    }

    override fun calculateExpression(): String {
        bracketsCloser(getUserExpression().getExpression())
        calculatorInstance.calculate(false)
        return getCalculatedExpression().toString()
    }

    override fun getCalculatedExpression(): Double {
        return calculatorInstance.result
    }

    fun setSwitchAngleMode(): AngleMode {
        currentAngleMode = when(currentAngleMode) {
            AngleMode.RAD -> AngleMode.DEG
            AngleMode.DEG -> AngleMode.RAD
        }
        calculatorInstance.setNewAngleMode(currentAngleMode)
        return currentAngleMode
    }

    fun closeExpressionString(): String {
        val sourceExpression = calculatorInstance.closeExpressionString(getUserExpression().getExpression())!!
        userExpression.setNewExpression(sourceExpression)
        calculatorInstance.setNewExpression(getUserExpression())
        return sourceExpression
    }

    // Закрывает большное количество скобок (если нужно)
    private fun bracketsCloser(expression: String) {
        userExpression.setNewExpression(bracketCloser.closeExpression(expression))
    }

}