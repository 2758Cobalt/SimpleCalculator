package com.cobaltumapps.simplecalculator.v15.converter.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardMathOperationsListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumbersListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.services.tallback.VibrationSingleton

class ConverterNumpadController(private var controllerListener: ConverterNumpadControllerListener?):
    KeyboardNumbersListener,
    KeyboardSpecialFunctionsListener,
    KeyboardMathOperationsListener,
    ConverterUserInputHandlerListener
{
    private val converterUserInputHandler = ConverterUserInputHandler(this@ConverterNumpadController)

    override fun onClickNumber(number: Number) {
        converterUserInputHandler.onClickNumber(number)
        VibrationSingleton.playVibration()
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        when(function) {
            KeyboardSpecialFunction.MemorySave -> {}
            KeyboardSpecialFunction.MemoryRead -> {}
            KeyboardSpecialFunction.MemoryClear -> {}
            KeyboardSpecialFunction.Enter -> { controllerListener?.confirmEntry() }
            else -> KeyboardSpecialFunction.Skip
        }
        converterUserInputHandler.onClickSpecialFunction(function)
    }

    override fun onClickMathOperation(operation: KeyboardArifmeticOperation) {
        converterUserInputHandler.onClickMathOperation(operation)
    }

    override fun receiveUserEntry(receivedEntry: String) {
        controllerListener?.receiveUserEntry(receivedEntry)
    }

}