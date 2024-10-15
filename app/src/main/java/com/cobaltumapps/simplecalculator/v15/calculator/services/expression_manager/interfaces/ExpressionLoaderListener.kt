package com.cobaltumapps.simplecalculator.v15.calculator.services.expression_manager.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.expression.Expression

interface ExpressionLoaderListener {
    fun loadExpression(): Expression
}