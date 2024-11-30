package com.cobaltumapps.simplecalculator.v15.converter.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardNumbersListener
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces.KeyboardSpecialFunctionsListener
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction

class ConverterNumpadController: KeyboardNumbersListener, KeyboardSpecialFunctionsListener {
    override fun onClickNumber(number: Number) {
        TODO("Нужно реализовать обработку нажатия на число")
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
                TODO("Нужно реализовать функцию очистки")
            }
            KeyboardSpecialFunction.Backspace -> {
                TODO("Нужно реализовать функцию стирания")
            }
            else -> { KeyboardSpecialFunction.Skip }
        }
    }
}