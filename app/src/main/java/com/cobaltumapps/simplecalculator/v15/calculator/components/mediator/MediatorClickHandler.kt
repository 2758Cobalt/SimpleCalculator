package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

interface MediatorClickHandler {
    fun handleNumberClick(number: Number)
    fun handleMathOperationClick(operation: MathOperation)
    fun handleSpecialOperationClick(operation: KeyboardSpecialOperation)
    fun handleSpecialFunctionClick(function: KeyboardSpecialFunction)
}