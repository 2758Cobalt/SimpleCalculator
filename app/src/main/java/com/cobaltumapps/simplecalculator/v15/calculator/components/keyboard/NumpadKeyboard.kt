package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardMaster
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

// Наследуем NumpadKeyboard от обобщённого класса KeyboardMaster с типом NumpadController
open class NumpadKeyboard : KeyboardMaster(), KeyboardNumpadListener {

    override fun onClickNumber(number: Number) {
        (keyboardController as NumpadController).onClickNumber(number)
    }

    override fun onClickMathOperation(operation: MathOperation) {
        (keyboardController as NumpadController).onClickMathOperation(operation)
    }

    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        (keyboardController as NumpadController).onClickSpecialOperation(operation)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        (keyboardController as NumpadController).onClickSpecialFunction(function)
    }
}

