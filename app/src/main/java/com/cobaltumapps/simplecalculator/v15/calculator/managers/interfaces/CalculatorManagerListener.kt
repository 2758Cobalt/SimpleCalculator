package com.cobaltumapps.simplecalculator.v15.calculator.managers.interfaces

interface CalculatorManagerListener:
    ConfiguratorExpression,
    CalculatorExpressionRemover,
    MemoryConfiguratorExpression,
    AngleModeConfigurator
{
    fun requestCalculateExpression(): Double
}

