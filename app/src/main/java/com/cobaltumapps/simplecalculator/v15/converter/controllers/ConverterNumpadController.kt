package com.cobaltumapps.simplecalculator.v15.converter.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumbersListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationSingleton

class ConverterNumpadController(private var listener: ConverterUserInputHandlerListener?): KeyboardNumbersListener, KeyboardSpecialFunctionsListener, ConverterUserInputHandlerListener {
    private val converterUserInputHandler = ConverterUserInputHandler(this@ConverterNumpadController)

    override fun onClickNumber(number: Number) {
        converterUserInputHandler.onClickNumber(number)
        VibrationSingleton.playVibration()
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        converterUserInputHandler.onClickSpecialFunction(function)
    }

    override fun receiveUserInput(receivedInput: String) {
        listener?.receiveUserInput(receivedInput)
    }

}