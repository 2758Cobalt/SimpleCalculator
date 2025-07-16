package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialOperation

class MediatorPointInputController: MediatorClickHandler, MediatorPointInputListener {
    private var inputedNumber = false
    private var allowPointInput = false
    private var pointUsed = false // Флаг для отслеживания ввода точки

    override fun handleOnClickNumber(number: Number) {
        inputedNumber = true
        if (!pointUsed) {
            allowPointInput = true // Разрешаем ввод точки, если её ещё не вводили
        }
    }

    override fun handleOnClickMathOperation(operation: KeyboardArifmeticOperation) {
        inputedNumber = false // Операция сбрасывает ввод числа
        allowPointInput = false // Нужно ввести цифру перед точкой
        pointUsed = false // После операции можно снова вводить точку

        if (operation == KeyboardArifmeticOperation.Point && inputedNumber && !pointUsed) {
            allowPointInput = false // Запрещаем повторный ввод точки
            pointUsed = true // Фиксируем, что точка была введена
        }
    }

    override fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation) {
    }

    override fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction) {
    }

    override fun isAllowPointInput(onAllowed: ((condition: Boolean) -> Unit?)?) {
        onAllowed?.invoke(allowPointInput)
    }
}