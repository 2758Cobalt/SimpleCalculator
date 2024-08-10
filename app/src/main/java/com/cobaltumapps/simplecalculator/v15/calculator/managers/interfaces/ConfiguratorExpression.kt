package com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces

interface ConfiguratorExpression {
    fun addToExpression(input: String)
    fun getExpression(): String
    fun setExpression(newExpression: String)
    fun clearExpression()
}

