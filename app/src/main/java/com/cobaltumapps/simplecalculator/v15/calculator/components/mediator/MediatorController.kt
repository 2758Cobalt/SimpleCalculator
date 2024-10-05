package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.HistoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.recycler.HistoryData
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryControllerImpl

/* Класс-посредник который взаимодействует с нужными классами и их методами  */
class MediatorController: MediatorClickHandler {
    // Calculator
    var calculatorController: CalculatorController? = null

    // Display
    var displayController: DisplayController? = null

    // Keyboards
    var numpadController: NumpadController? = null
    var engNumpadController: EngineeringController? = null

    // Services
    var historyService: HistoryControllerImpl? = null
    var memoryService: MemoryControllerImpl? = null

    // Обработка клика (число)
    override fun handleNumberClick(number: Number) {
        val newExpression = calculatorController?.addToExpression(number.toString())!!
        displayController?.setExpressionField(newExpression)
    }

    // Обрабокта клика (мат. операция)
    override fun handleMathOperationClick(operation: MathOperation) {
        val newExpression = calculatorController?.addToExpression(operation.symbol.toString())!!
        displayController?.setExpressionField(newExpression)
    }

    // Обработка клика (спец. операция)
    override fun handleSpecialOperationClick(operation: KeyboardSpecialOperation) {
        // Тут описываются только особенные операции, если такие есть
        val newExpression = calculatorController?.addToExpression(operation.symbol)!!
        displayController?.setExpressionField(newExpression)
    }

    // Обработка клика (спец. функция)
    override fun handleSpecialFunctionClick(function: KeyboardSpecialFunction) {
        when(function) {

            // Функция - равно (=)
            KeyboardSpecialFunction.Equal -> {
                calculatorController?.let {
                    val userExpression = it.getUserExpression().getExpression()
                    val calculatedResult = it.calculateExpression()

                    displayController?.setExpressionField(userExpression)
                    displayController?.setResultField(calculatedResult)

                    historyService?.addExpressionItem(HistoryData(userExpression, calculatedResult))
                }
            }

            // Функция - очистки последнего символа (Backspace)
            KeyboardSpecialFunction.Backspace -> {
                calculatorController?.removeLastSymbolExpression()
                displayController?.setExpressionField(calculatorController?.getUserExpression()?.getExpression()!!)
                displayController?.clearResultField()
            }

            // Функция - всё очистить (All clear)
            KeyboardSpecialFunction.AllClear -> {
                displayController?.allClearFields()
                calculatorController?.clearExpression()
            }

            // Функция - удаление группы символов
            KeyboardSpecialFunction.GroupDigitsCleaning -> {
                calculatorController?.removeDigitsGroup()
                displayController?.setExpressionField(calculatorController?.getUserExpression()?.getExpression()!!)
            }

            KeyboardSpecialFunction.Invert -> {
                displayController?.setExpressionField(calculatorController?.closeExpressionString()!!)
            }

//            KeyboardSpecialFunction.MemorySave -> {
//                memoryService?.saveMemoryValue(calculatorController?.getCalculatedExpression()!!)
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }
//
//            KeyboardSpecialFunction.MemoryRead -> {
//                calculatorController?.addToExpression(memoryService?.readMemory().toString())
//                updateDisplay()
//            }
//
//            KeyboardSpecialFunction.MemoryClear -> {
//                memoryService?.clearMemory()
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }
//
//            KeyboardSpecialFunction.MemoryAdd -> {
//                val result = memoryService?.readMemory()!! + calculatorController?.getCalculatedExpression()?.toFloat()!!
//                memoryService?.saveMemoryValue(result)
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }
//
//            KeyboardSpecialFunction.MemorySubtract -> {
//                val result = memoryService?.readMemory()!! - calculatorController?.getCalculatedExpression()?.toFloat()!!
//                memoryService?.saveMemoryValue(result)
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }
//
//            KeyboardSpecialFunction.MemoryMultiply -> {
//                val result = memoryService?.readMemory()!! * calculatorController?.getCalculatedExpression()?.toFloat()!!
//                memoryService?.saveMemoryValue(result)
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }
//
//            KeyboardSpecialFunction.MemoryDivide -> {
//                val result = memoryService?.readMemory()!! / calculatorController?.getCalculatedExpression()?.toFloat()!!
//                memoryService?.saveMemoryValue(result)
//                displayController?.setMemoryField(memoryService?.readMemory()!!)
//            }

//            KeyboardSpecialFunction.AngleMode -> {
//                val newAngleMode = calculatorController?.setSwitchAngleMode()
//                displayController?.setAngleField(newAngleMode!!)
//            }

            // Функция - пропускает действие (если нужно)
            KeyboardSpecialFunction.Skip -> {
                Log.d(LOG_TAG, "The special function is skipped")
            }

            else -> KeyboardSpecialFunction.Skip
        }
    }

//    override fun onHistoryItemClicked(expression: String) {
//        calculatorController?.setExpression(expression)
//        updateDisplay()
//    }


    private fun updateDisplay() {
        displayController?.setExpressionField(
            calculatorController?.getUserExpression()?.getExpression()!!
        )
    }

    companion object {
        const val LOG_TAG = "SC_MediatorTag"
    }
}