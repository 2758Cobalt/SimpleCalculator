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
    private var userEntry = "0"

    override fun onClickNumber(number: Number) {
        if (userEntry == "0") {
            userEntry = number.toString()
        } else {
            userEntry += number.toString()
        }

        receiveUserEntry(userEntry)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        when(function) {
            KeyboardSpecialFunction.AllClear -> {
                userEntry = "0"
                receiveUserEntry(userEntry)
            }

            KeyboardSpecialFunction.Backspace -> {
                if (userEntry.isNotEmpty()) {
                    val useInputBuilder = StringBuilder(userEntry)

                    useInputBuilder.deleteCharAt(userEntry.lastIndex)
                    userEntry = useInputBuilder.toString()
                    if (userEntry.isEmpty())
                        userEntry = "0"
                }
                else
                    userEntry = "0"

                receiveUserEntry(userEntry)
            }

            else -> {
                KeyboardSpecialFunction.Skip
            }
        }
    }

    override fun onClickMathOperation(operation: KeyboardArifmeticOperation) {
        when(operation) {
            KeyboardArifmeticOperation.Point -> {
                userEntry += operation.symbol
                receiveUserEntry(userEntry)
            }

            else -> KeyboardArifmeticOperation.None
        }
    }

    override fun receiveUserEntry(receivedEntry: String) {
        listener?.receiveUserEntry(receivedEntry)
    }
}