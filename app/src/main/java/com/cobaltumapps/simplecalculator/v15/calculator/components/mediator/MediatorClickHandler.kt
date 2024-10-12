package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

interface MediatorClickHandler {
    fun handleOnClickNumber(number: Number)
    fun handleOnClickMathOperation(operation: MathOperation)
    fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation)
    fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction)
}