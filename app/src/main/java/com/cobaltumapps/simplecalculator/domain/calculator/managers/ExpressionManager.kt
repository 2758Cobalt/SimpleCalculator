package com.cobaltumapps.simplecalculator.domain.calculator.managers

import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression
import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.interfaces.ExpressionBasicOperations
import com.cobaltumapps.simplecalculator.v15.calculator.managers.ExpressionRemover
import com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces.CalculatorExpressionRemover

class ExpressionManager: ExpressionBasicOperations, CalculatorExpressionRemover {
    private var userExpression = Expression()
    private val expressionRemover = ExpressionRemover()

    override fun setNewExpression(newExpression: String): String = userExpression.setNewExpression(newExpression)

    override fun getExpression(): String = userExpression.getExpression()

    fun getExpressionObject(): Expression = userExpression

    override fun clearExpression() { userExpression.clearExpression().let { userExpression.getExpression() } }

    fun addSymbol(element: String): String {
        val new = userExpression.getExpression() + element
        return userExpression.setNewExpression(new)
    }

    override fun removeLastSymbolExpression(sourceString: String): String {
        val new = expressionRemover.removeLastSymbolExpression(userExpression.getExpression())
        return userExpression.setNewExpression(new)
    }

    override fun removeDigitsFromEnd(sourceString: String): String {
        val new = expressionRemover.removeDigitsFromEnd(userExpression.getExpression())
        return userExpression.setNewExpression(new)
    }
}