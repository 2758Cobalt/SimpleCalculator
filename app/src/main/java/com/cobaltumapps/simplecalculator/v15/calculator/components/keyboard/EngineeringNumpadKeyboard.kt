package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardMaster
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation

open class EngineeringNumpadKeyboard : KeyboardMaster(), KeyboardSpecialFunctionsListener {

    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        (keyboardController as EngineeringController).onClickSpecialOperation(operation)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        (keyboardController as EngineeringController).onClickSpecialFunction(function)
    }
}