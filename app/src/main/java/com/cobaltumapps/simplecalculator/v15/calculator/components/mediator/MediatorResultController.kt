package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialOperation

class MediatorResultController: MediatorClickHandler, MediatorResultListener {
    private var isResult = false

    override fun handleOnClickNumber(number: Number) {
        isResult = false
    }

    override fun handleOnClickMathOperation(operation: KeyboardArifmeticOperation) {
        isResult = false
    }

    override fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation) {
        isResult = false
    }

    override fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction) {
        isResult = if (function == KeyboardSpecialFunction.Equal) {
            !isResult
        } else {
            false
        }
    }

    override fun isResultCondition(onCalculatedResult: ((condition: Boolean) -> Unit?)?) {
        onCalculatedResult?.invoke(isResult)
    }
}