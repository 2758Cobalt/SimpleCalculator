package com.cobaltumapps.simplecalculator.v15.calculator.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.enums.ActionOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.NumpadListener
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.logger.NumpadInputControllerLogger

// Котроллер, который контроллирует действия numpad
class NumpadInputController: InputController(), NumpadListener {

    // Создание логера для логирования
    private val numpadInputControllerLogger = NumpadInputControllerLogger()

    // Обработка нажатий и действий
    override fun onClickNumber(number: Number) {
        numpadInputControllerLogger.onClickNumber(number)
        calculatorManagerListener?.addToExpression(number.toString())
    }

    override fun onClickOperation(operation: MathOperation) {
        numpadInputControllerLogger.onClickOperation(operation)

        val symbol = operation.symbol.toString()
        calculatorManagerListener?.addToExpression(symbol)
    }

    // Обработка нажатий на действия (Equal, AC, MemorySave...)
    override fun onClickAction(action: ActionOperation) {
        numpadInputControllerLogger.onClickAction(action)

        when(action) {
            ActionOperation.Equal -> { onActionEqual() }
            ActionOperation.AC -> { onActionAllClear() }
        }
    }

    override fun onActionBackspace() {
        numpadInputControllerLogger.onActionBackspace()
        calculatorManagerListener?.removeLastSymbolExpression(calculatorManagerListener?.getExpression()!!)
    }

    override fun onActionGroupClean() {
        numpadInputControllerLogger.onActionGroupClean()
        calculatorManagerListener?.removeDigitsFromEnd(calculatorManagerListener?.getExpression()!!)

    }

    override fun onActionAllClear() {
        numpadInputControllerLogger.onActionAllClear()
        calculatorManagerListener?.clearExpression()
    }

    override fun onActionEqual() {
        numpadInputControllerLogger.onActionEqual()
        calculatorManagerListener?.requestCalculateExpression()
    }

}


