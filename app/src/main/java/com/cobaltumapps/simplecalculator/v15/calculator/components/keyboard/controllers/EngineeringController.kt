package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.loggers.EngineeringLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialOperationListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardControllerMaster
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation

class EngineeringController : KeyboardControllerMaster(), KeyboardSpecialFunctionsListener, KeyboardSpecialOperationListener {
    private val engineeringLogger = EngineeringLogger()

    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        mediator?.handleOnClickSpecialOperation(operation)
        engineeringLogger.onClickSpecialOperation(operation)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        mediator?.handleOnClickSpecialFunction(function)
        engineeringLogger.onClickSpecialFunction(function)
    }
}