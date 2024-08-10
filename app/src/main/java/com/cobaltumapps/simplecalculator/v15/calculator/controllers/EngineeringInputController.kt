package com.cobaltumapps.simplecalculator.v15.calculator.controllers

import com.cobaltumapps.simplecalculator.v15.calculator.enums.AngleMode
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MemoryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.TrigonometryOperation
import com.cobaltumapps.simplecalculator.v15.calculator.host.interfaces.EngineeringNumpadKeyboard
import com.cobaltumapps.simplecalculator.v15.calculator.numpad.engineering.EngineeringInputControllerLogger

// Котроллер, который контроллирует вводимое число, знак или операцию
class EngineeringInputController: InputController(), EngineeringNumpadKeyboard {
    private val engineeringInputControllerLogger = EngineeringInputControllerLogger()

    // Обработка нажатия на операцию
    override fun onClickOperation(operation: MathOperation) {
        engineeringInputControllerLogger.onClickOperation(operation)
    }

    // Обработка нажатий и действий
    override fun onClickMemoryOperation(memoryOperation: MemoryOperation) {
        engineeringInputControllerLogger.onClickMemoryOperation(memoryOperation)
        when(memoryOperation) {
            MemoryOperation.MemorySave -> { memoryControllerInstance?.actionSave() }
            MemoryOperation.MemoryRead -> { memoryControllerInstance?.actionRead() }
            MemoryOperation.MemoryClear -> { memoryControllerInstance?.actionClear() }

            MemoryOperation.MemoryAdd -> memoryControllerInstance?.actionMemoryAdd()
            MemoryOperation.MemorySub -> memoryControllerInstance?.actionMemorySub()
            MemoryOperation.MemoryMul -> memoryControllerInstance?.actionMemoryMul()
            MemoryOperation.MemoryDiv -> memoryControllerInstance?.actionMemoryDiv()
        }
    }

    // Обработка вызовов тригонометрических операций
    override fun onClickTrigonometryOperation(trigonometryOperation: TrigonometryOperation) {
        engineeringInputControllerLogger.onClickTrigonometryOperation(trigonometryOperation)
        when(trigonometryOperation) {
            TrigonometryOperation.Sine -> calculatorManagerListener?.addToExpression("sin(")
            TrigonometryOperation.Cosine -> calculatorManagerListener?.addToExpression("cos(")
            TrigonometryOperation.Tangent -> calculatorManagerListener?.addToExpression("tan(")
            TrigonometryOperation.Cotangent -> calculatorManagerListener?.addToExpression("cot(")
        }
    }

    override fun onClickAngleMode(angleMode: AngleMode) {
        calculatorManagerListener?.
    }
}