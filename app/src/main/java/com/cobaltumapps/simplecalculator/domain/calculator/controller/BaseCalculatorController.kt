package com.cobaltumapps.simplecalculator.domain.calculator.controller

import com.cobaltumapps.simplecalculator.data.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.domain.calculator.core.BaseCalculator
import com.cobaltumapps.simplecalculator.domain.calculator.expression.ExpressionCloser
import com.cobaltumapps.simplecalculator.domain.calculator.managers.AngleModeManager
import com.cobaltumapps.simplecalculator.domain.calculator.managers.ExpressionManager
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.BracketCloser
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.interfaces.ExpressionBasicOperations
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover
import java.math.BigDecimal

class BaseCalculatorController(private val calculatorInstance: BaseCalculator): ExpressionBasicOperations,
    CalculatorExpressionRemover, ExpressionCloser {

    private val expressionManager = ExpressionManager()
    private val angleModeManager = AngleModeManager()
    private val bracketCloser = BracketCloser()

    private fun updateCalculatorExpression() {
        calculatorInstance.setNewExpression(expressionManager.getExpressionObject())
    }

    fun addSymbol(symbol: String): String {
        val newExpression = expressionManager.addSymbol(symbol)
        updateCalculatorExpression()
        return newExpression
    }

    fun calculateExpression(): String {
        closeExpression(expressionManager.getExpression())
        calculatorInstance.evaluate()
        return getCalculatedExpression().toString()
    }

    fun getCalculatedExpression(): BigDecimal {
        return calculatorInstance.calculationResult
    }

    fun setSwitchAngleMode(): AngleMode {
        angleModeManager.switchMode {
            calculatorInstance.angleMode = it
        }
        return angleModeManager.getAngleMode()
    }

    fun closeExpressionString(): String {
        val sourceExpression = calculatorInstance.closeExpressionString(expressionManager.getExpression())
        expressionManager.setNewExpression(sourceExpression)
        calculatorInstance.setNewExpression(expressionManager.getExpressionObject())
        return sourceExpression
    }

    override fun closeExpression(sourceExpression: String): String {
        return expressionManager.setNewExpression(bracketCloser.closeExpression(sourceExpression))
    }

    override fun setNewExpression(newExpression: String): String {
        return expressionManager.setNewExpression(newExpression).also { updateCalculatorExpression() }
    }

    override fun getExpression(): String =
        expressionManager.getExpression()

    override fun clearExpression() { expressionManager.clearExpression(); updateCalculatorExpression() }

    override fun removeLastSymbolExpression(sourceString: String): String =
        expressionManager.removeLastSymbolExpression(sourceString)

    fun removeLastSymbolExpression(): String =
        expressionManager.removeLastSymbolExpression(expressionManager.getExpression())

    override fun removeDigitsFromEnd(sourceString: String): String =
        expressionManager.removeDigitsFromEnd(sourceString)

    fun removeDigitsFromEnd(): String =
        expressionManager.removeDigitsFromEnd(expressionManager.getExpression())
}