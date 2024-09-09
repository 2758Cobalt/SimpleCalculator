package com.cobaltumapps.simplecalculator.v15.calculator.managers

import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore

// Менеджер калькулятора, который "Общается" с экземпляром калькулятора
class CalculatorManager: CalculatorManagerListener, CalculatorExpressionRemover {
    private var calculatorInstance: CalculatorCore? = null
    private var calculatorExpressionRemover = ExpressionRemover()

    private var expression = ""

    // Установка слушателей
    fun setCalculatorInstance(calculator: CalculatorCore) {
        this.calculatorInstance = calculator
        this.calculatorInstance?.calculatorManagerInstance = this
    }

    // Устанавливает новое выражение
    override fun setExpression(newExpression: String) {
        this.expression = newExpression
        sendExpressionUpdated()
    }

    // Добавляет к выражению ввод (цифру, символ и тд)
    override fun addToExpression(input: String) {
        calculatorInstance.let {
            // Обновляем выражение в калькуляторе
            sendExpressionUpdated()
        }
    }

    // Удаляет последний символ в выражении
    override fun removeLastSymbolExpression(sourceString: String): String {
        val result = calculatorExpressionRemover.removeLastSymbolExpression(sourceString)

        setExpression(result)
        sendExpressionUpdated()
        return result
    }

    // Удаление группы цифр с конца выражения
    override fun removeDigitsFromEnd(sourceString: String): String {
        val result = calculatorExpressionRemover.removeDigitsFromEnd(sourceString)

        setExpression(result)
        sendExpressionUpdated()
        return result
    }

    override fun setExpressionFromMemory(input: String) {
        setExpression(input)
    }

    override fun setAngleMode(angleMode: AngleMode) {
    }

    // Возврат выражения
    override fun getExpression(): String {
        return expression
    }

    // Очистка выражения
    override fun clearExpression() {
        this.expression = ""

        sendExpressionUpdated() // Обновление выражения
    }

    // Расчитывает выражение
    override fun requestCalculateExpression(): Double {
        calculatorInstance.let {
            calculatorInstance!!.calculate()

            return calculatorInstance!!.result
        }
    }

    // Обновляет выражение в калькуляторе и мониторе
    private fun sendExpressionUpdated() {
        calculatorInstance.let {
            calculatorInstance?.setNewExpression(Expression(expression))
        }
    }

}

