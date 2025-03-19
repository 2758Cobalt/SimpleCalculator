package com.cobaltumapps.simplecalculator.v15.converter.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumbersListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction

class ConverterUserInputHandler(private val listener: ConverterUserInputHandlerListener? = null): KeyboardNumbersListener, KeyboardSpecialFunctionsListener, ConverterUserInputHandlerListener {
    private var userInput = "0"

    override fun onClickNumber(number: Number) {
        if (userInput == "0") {
            userInput = number.toString()
        } else {
            userInput += number.toString()
        }

        receiveUserInput(userInput)
    }

    override fun onClickSpecialFunction(function: KeyboardSpecialFunction) {
        when(function) {
            KeyboardSpecialFunction.MemorySave -> {
                TODO("Нужно реализовать функцию сохранения в память")
            }
            KeyboardSpecialFunction.MemoryRead -> {
                TODO("Нужно реализовать функцию чтение из памяти")
            }
            KeyboardSpecialFunction.MemoryClear -> {
                TODO("Нужно реализовать функцию очистку памяти")
            }
            KeyboardSpecialFunction.AllClear -> {
                userInput = "0"
                receiveUserInput(userInput)
            }
            KeyboardSpecialFunction.Backspace -> {
                if (userInput.isNotEmpty()) {
                    val useInputBuilder = StringBuilder(userInput)

                    useInputBuilder.deleteCharAt(userInput.lastIndex)
                    userInput = useInputBuilder.toString()
                }

                receiveUserInput(userInput)
            }
            else -> {
                KeyboardSpecialFunction.Skip
            }
        }
    }

    override fun receiveUserInput(receivedInput: String) {
        listener?.receiveUserInput(receivedInput)
    }
}