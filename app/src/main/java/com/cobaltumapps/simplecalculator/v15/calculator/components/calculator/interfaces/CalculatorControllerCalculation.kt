package com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.interfaces

/* Интерфейс контроллера калькулятора */
interface CalculatorControllerCalculation {
    fun calculateExpression(): String
    fun getCalculatedExpression(): Double
}