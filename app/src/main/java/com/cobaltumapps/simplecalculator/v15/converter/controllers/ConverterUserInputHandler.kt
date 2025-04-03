package com.cobaltumapps.simplecalculator.v15.converter.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardMathOperationsListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumbersListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction

class ConverterUserInputHandler(private val listener: ConverterUserInputHandlerListener? = null):
    KeyboardNumbersListener,
    KeyboardSpecialFunctionsListener,
    KeyboardMathOperationsListener,
    ConverterUserInputHandlerListener
{
    private var userInput = "0"

    override fun onClickNumber(number: Number) {
        if (userInput == "0") {
            userInput = number.toString()
        } else {
            userInput += number.toString()
        }

        receiveUserEntry(userInput)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        when(function) {
            KeyboardSpecialFunction.AllClear -> {
                userInput = "0"
                receiveUserEntry(userInput)
            }

            KeyboardSpecialFunction.Backspace -> {
                if (userInput.isNotEmpty()) {
                    val useInputBuilder = StringBuilder(userInput)

                    useInputBuilder.deleteCharAt(userInput.lastIndex)
                    userInput = useInputBuilder.toString()
                }

                receiveUserEntry(userInput)
            }

            else -> {
                KeyboardSpecialFunction.Skip
            }
        }
    }

    override fun onClickMathOperation(operation: KeyboardArifmeticOperation) {
        when(operation) {
            KeyboardArifmeticOperation.Point -> {
                userInput += operation.symbol
                receiveUserEntry(userInput)
            }

            else -> KeyboardArifmeticOperation.None
        }
    }

    override fun receiveUserEntry(receivedEntry: String) {
        listener?.receiveUserEntry(receivedEntry)
    }
}