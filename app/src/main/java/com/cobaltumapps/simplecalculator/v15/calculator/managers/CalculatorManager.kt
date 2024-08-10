package com.cobaltumapps.simplecalculator.v15.calculator.managers

import com.cobaltumapps.simplecalculator.v15.builders.ExpressionBuilder
import com.cobaltumapps.simplecalculator.v15.calculator.calculatorMonitor.CalculatorExpressionMonitor
import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.loggers.CalculatorManagerLogger
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorManagerListener
import com.cobaltumapps.simplecalculator.v15.calculator.models.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryController
import com.cobaltumapps.simplecalculator.v15.calculator.system.CalculatorCore

// Менеджер калькулятора, который "Общается" с экземпляром калькулятора
class CalculatorManager: CalculatorManagerListener, CalculatorExpressionRemover {
    private var memoryControllerInstance: MemoryController? = null


    private val expressionBuilder = ExpressionBuilder()

    private var calculatorInstance: CalculatorCore? = null
    private var calculatorExpressionMonitorInstance: CalculatorExpressionMonitor? = null

    private var calculatorManagerLogger = CalculatorManagerLogger()
    private var calculatorExpressionRemover = ExpressionRemover()

    private var expression = ""

    // Установка слушателей
    fun setCalculatorInstance(calculator: CalculatorCore) {
        this.calculatorInstance = calculator
        this.calculatorInstance?.calculatorManagerInstance = this
    }

    fun setMonitor(monitor: CalculatorExpressionMonitor) {
        calculatorExpressionMonitorInstance = monitor
    }
    fun setMemoryControllerInstance(instance: MemoryController) {
        memoryControllerInstance = instance
    }

    fun getResult(): String {
        return calculatorInstance!!.result.toString()
    }

    // Устанавливает новое выражение
    override fun setExpression(newExpression: String) {
        this.expression = newExpression
        sendExpressionUpdated()
        memoryControllerInstance?.canPerformAction = false
    }

    // Добавляет к выражению ввод (цифру, символ и тд)
    override fun addToExpression(input: String) {
        calculatorInstance.let {
            // Создаём объект Expression
            val expressionBuilt = expressionBuilder.buildExpression(this.expression, input)

            expression = expressionBuilt.expression

            // Обновляем выражение в калькуляторе
            sendExpressionUpdated()

            // Логирование
            calculatorManagerLogger.addToExpression(input)
            memoryControllerInstance?.canPerformAction = false
        }
    }

    // Удаляет последний символ в выражении
    override fun removeLastSymbolExpression(sourceString: String): String {
        calculatorManagerLogger.removeLastSymbolExpression(sourceString)

        val result = calculatorExpressionRemover.removeLastSymbolExpression(sourceString)

        setExpression(result)
        sendExpressionUpdated()

        return result
    }

    // Удаление группы цифр с конца выражения
    override fun removeDigitsFromEnd(sourceString: String): String {
        calculatorManagerLogger.removeLastSymbolExpression(sourceString)

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
        calculatorManagerLogger.getExpression() // Логирование
        return expression
    }

    // Очистка выражения
    override fun clearExpression() {
        this.expression = ""

        calculatorManagerLogger.clearExpression() // Логирование
        sendExpressionUpdated() // Обновление выражения
        memoryControllerInstance?.canPerformAction = false
    }

    // Расчитывает выражение
    override fun requestCalculateExpression(): Double {
        calculatorInstance.let {
            calculatorInstance!!.calculate()

            memoryControllerInstance?.canPerformAction = true
            calculatorExpressionMonitorInstance?.setCalculatedExpression(calculatorInstance!!.result.toString())

            // Логирование
            calculatorManagerLogger.requestCalculateExpression()
            return calculatorInstance!!.result
        }
    }

    // Обновляет выражение в калькуляторе и мониторе
    private fun sendExpressionUpdated() {
        calculatorInstance.let {

            calculatorInstance?.setNewExpression(Expression(expression))
            calculatorExpressionMonitorInstance?.setExpressionField(expression)
        }
    }

}

