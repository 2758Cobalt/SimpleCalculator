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

        listener?.receiveUserInput()
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
                listener?.receiveUserInput()
            }
            KeyboardSpecialFunction.Backspace -> {
                val useInputBuilder = StringBuilder(userInput)

                useInputBuilder.deleteCharAt(userInput.lastIndex)
                userInput = useInputBuilder.toString()
                listener?.receiveUserInput()
            }
            else -> {
                KeyboardSpecialFunction.Skip
            }
        }
    }

    override fun receiveUserInput(): String {
        return userInput
    }
}