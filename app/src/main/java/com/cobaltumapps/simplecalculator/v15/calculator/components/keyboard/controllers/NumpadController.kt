package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.NumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.loggers.NumpadLogger
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardControllerMaster
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardMaster
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationSingleton

// Котроллер, который контроллирует действия numpad
class NumpadController: KeyboardControllerMaster(), KeyboardNumpadListener {
    private val numpadLogger = NumpadLogger()

    override fun getInstance(instance: KeyboardMaster) {
        controlledKeyboardMaster = instance as NumpadKeyboard
    }

    // Обработка нажатий и действий
    override fun onClickNumber(number: Number) {
        mediator?.handleOnClickNumber(number)
        playVibration()
        numpadLogger.onClickNumber(number)
    }

    override fun onClickMathOperation(operation: KeyboardArifmeticOperation) {
        mediator?.handleOnClickMathOperation(operation)
        playVibration()
        numpadLogger.onClickMathOperation(operation)
    }

    override fun onClickSpecialOperation(operation: KeyboardSpecialOperation) {
        mediator?.handleOnClickSpecialOperation(operation)
        playVibration()
        numpadLogger.onClickSpecialOperation(operation)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        mediator?.handleOnClickSpecialFunction(function)
        playVibration()
        numpadLogger.onClickSpecialFunction(function)
    }

    private fun playVibration() {
        VibrationSingleton.playVibration()
    }
}