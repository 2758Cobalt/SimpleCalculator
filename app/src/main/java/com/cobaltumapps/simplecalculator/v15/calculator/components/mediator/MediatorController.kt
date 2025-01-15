package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.v15.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.enums.MathOperation
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.PreferencesUserData
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.interfaces.PreferencesUpdaterListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.DateTimeService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryControllerImpl
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History

/* Класс-посредник который взаимодействует с нужными классами и их методами  */
class MediatorController: MediatorClickHandler, HolderOnClickListener, PreferencesUpdaterListener {
    private var preferencesUserData = PreferencesUserData()
    // Calculator
    var calculatorController: CalculatorController? = null

    // Display
    var displayController: DisplayController? = null

    // Keyboards
    var numpadController: NumpadController? = null
    var engNumpadController: EngineeringController? = null

    // ServiceClipboard
    var historyService: CalculatorHistoryController? = null
    var memoryService: MemoryControllerImpl? = null

    private val dateTimeService = DateTimeService()

    var userExpression = ""
    var calculatedResult = ""

    // Обработка клика (число)
    override fun handleOnClickNumber(number: Number) {
        val newExpression = calculatorController?.addToExpression(number.toString())!!
        displayController?.setExpressionField(newExpression)
    }

    // Обрабокта клика (мат. операция)
    override fun handleOnClickMathOperation(operation: MathOperation) {
        val newExpression = calculatorController?.addToExpression(operation.symbol.toString())!!
        displayController?.setExpressionField(newExpression)
    }

    // Обработка клика (спец. операция)
    override fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation) {
        // Тут описываются только особенные операции, если такие есть
        val newExpression = calculatorController?.addToExpression(operation.symbol)!!
        displayController?.setExpressionField(newExpression)
    }

    // Обработка клика (спец. функция)
    override fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction) {
        try {
            when(function) {
                // Функция - равно (=)
                KeyboardSpecialFunction.Equal -> {
                    // Обновляет поля выражения и результата вычисления
                    updateCalculationFields()

                    // Устанавливает результат вычислений
                    displayController?.setResultField(calculatedResult)

                    // Запись вычислений в историю
                    makeHistoryRecord()

                    // Проверка предпочтения и выполнение действия, если истина
                    if (preferencesUserData.memoryAutoSave)
                        handleOnClickSpecialFunction(KeyboardSpecialFunction.MemorySave)
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

                // MemoryStorageManager
                KeyboardSpecialFunction.MemorySave -> {
                    memoryService?.saveMemoryValue(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryRead -> {
                    calculatorController?.setExpression(memoryService?.readMemory()!!)
                    updateDisplayExpression()
                }

                KeyboardSpecialFunction.MemoryClear -> {
                    memoryService?.clearMemory { result -> displayController?.setMemoryField(result) }
                }

                // MemoryStorageManager operations
                KeyboardSpecialFunction.MemoryAdd -> {
                    memoryService?.addToMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemorySubtract -> {
                    memoryService?.subtractFromMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryMultiply -> {
                    memoryService?.multiplyToMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryDivide -> {
                    memoryService?.divideAtMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                // Angle mode
                KeyboardSpecialFunction.AngleMode -> {
                    displayController?.setAngleField(
                        calculatorController?.setSwitchAngleMode()!!
                    )
                }

                // Функция - пропускает действие (если нужно)
                KeyboardSpecialFunction.Skip -> {
                    Log.d(LOG_TAG, "The special function is skipped")
                }

                else -> KeyboardSpecialFunction.Skip
            }
        }
        catch (ex: NullPointerException) {
            Log.e(LOG_TAG, "The special function is skipped")
        }

    }

    /** При нажатии на элемент "Истории" - устанавливает в калькулятор выбраное выражение. Возвращает выбраное выражение */
    override fun onHistoryItemClicked(expression: String): String {
        calculatorController?.setExpression(expression)
        updateDisplayExpression()
        return expression
    }

    /** Метод, обновляющий предпочтения (нгстройки) */
    override fun updatePreferences(newPrefConfig: PreferencesUserData) {
        this.preferencesUserData = newPrefConfig
        Log.i(LOG_TAG, "preferences in mediator has been updated:")
        Log.i(LOG_TAG, "Updated data:\n" +
                "autoSaveMemory: ${newPrefConfig.memoryAutoSave}\n" +
                "keepLastRecord: ${newPrefConfig.keepLastRecord}\n" +
                "allowVibration: ${newPrefConfig.allowVibration}")
    }

    /** Обновление дисплея */
    private fun updateDisplayExpression() {
        displayController?.setExpressionField(
            calculatorController?.getUserExpression()?.getExpression()!!
        )
    }

    /** Обновляет локальные поля */
    private fun updateCalculationFields() {
        calculatorController?.let {
            userExpression = it.getUserExpression().getExpression()
            calculatedResult = it.calculateExpression()
        }
    }

    /** Добавляем в историю новый элемент */
    private fun makeHistoryRecord() {
        historyService?.addHistoryItem(
            History(
                null,
                userExpression,
                calculatedResult,
                dateTimeService.getUnixTime()
            )
        )
    }

    companion object {
        const val LOG_TAG = "SC_MediatorController" +
                "Tag"
    }
}