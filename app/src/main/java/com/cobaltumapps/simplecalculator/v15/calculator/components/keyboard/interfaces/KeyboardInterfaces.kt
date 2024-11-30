package com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.master.KeyboardMaster
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation

interface KeyboardNumpadListener:
    KeyboardNumbersListener,
    KeyboardMathOperationsListener,
    KeyboardSpecialFunctionsListener,
    KeyboardSpecialOperationListener

// Интерфейс, отвечающий за передачу экземпляра контроллеру
interface KeyboardInstanceSender {
    fun sendInstance(instance: KeyboardMaster)
}

// Интерфейс, отвечающий за получение в контроллере экземпляра клавиатуры
interface KeyboardInstanceGetter {
    fun getInstance(instance: KeyboardMaster)
}

// Интерфейс, отвечающий за нажатие цифр
interface KeyboardNumbersListener {
    fun onClickNumber(number: Number)
}

// Интерфейс, отвечающий за нажатие арифметичекой операции
interface KeyboardMathOperationsListener {
    fun onClickMathOperation(operation: MathOperation)
}

// Интерфейс, отвечающий за нажатия на функцию
interface KeyboardSpecialFunctionsListener {
    fun onClickSpecialFunction(function: KeyboardSpecialFunction)
}

interface KeyboardSpecialOperationListener {
    fun onClickSpecialOperation(operation: KeyboardSpecialOperation)
}