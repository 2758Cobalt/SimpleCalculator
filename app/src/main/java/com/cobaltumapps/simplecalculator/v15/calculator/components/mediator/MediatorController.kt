package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import android.util.Log
import com.cobaltumapps.simplecalculator.v15.calculator.components.calculator.CalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayExpressionController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryStorageControllerSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys
import com.cobaltumapps.simplecalculator.v15.references.LogTags

/* Класс-посредник который взаимодействует с нужными классами и их методами  */
class MediatorController: MediatorClickHandler, HolderOnClickListener, MediatorResultListener {
    // Mediator
    private val mediatorResultController = MediatorResultController()

    // Calculator
    var calculatorController: CalculatorController? = null

    // Display
    var displayController: DisplayExpressionController? = null

    // Keyboards
    var numpadController: NumpadController? = null
    var engNumpadController: EngineeringController? = null

    // Services
    var historyService: CalculatorHistoryController? = null
    private val memoryStorageManager by lazy { MemoryStorageControllerSingleton.getInstance() }

    private val calendarService = CalendarService()

    // Обработка клика (число)
    override fun handleOnClickNumber(number: Number) {
        mediatorResultController.handleOnClickNumber(number)

        addToExpression(number.toString())
    }

    // Обрабокта клика (мат. операция)
    override fun handleOnClickMathOperation(operation: KeyboardArifmeticOperation) {
        mediatorResultController.handleOnClickMathOperation(operation)
        addToExpression(operation.symbol.toString())
    }

    // Обработка клика (спец. операция)
    override fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation) {
        mediatorResultController.handleOnClickSpecialOperation(operation)
        addToExpression(operation.symbol)
    }

    // Обработка клика (спец. функция)
    override fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction) {
        try {
            when(function) {
                // Функция - равно (=)
                KeyboardSpecialFunction.Equal -> {
                    mediatorResultController.isResultCondition { isResult ->
                        if (isResult) {
                            val result = updateCalculationFields()

                            calculatorController?.setExpression(result.second)

                            displayController?.setExpressionField(result.second)
                            displayController?.clearResultField()
                        }
                        else {
                            // Обновляет поля выражения и результата вычисления и возвращает Pair<Выражение, результат вычисления>
                            val result = updateCalculationFields()

                            // Устанавливает результат вычислений
                            displayController?.setResultField(result.second)

                            makeHistoryRecordFeature(result)

                            saveResultToMemoryFeature()
                        }

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

                // MemoryStorageManager
                KeyboardSpecialFunction.MemorySave -> {
                    memoryStorageManager.saveMemoryValue(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryRead -> {
                    calculatorController?.setExpression(memoryStorageManager.readMemory())
                    updateDisplayExpression()
                }

                KeyboardSpecialFunction.MemoryClear -> {
                    memoryStorageManager.clearMemory { result -> displayController?.setMemoryField(result) }
                }

                // MemoryStorageManager operations
                KeyboardSpecialFunction.MemoryAdd -> {
                    memoryStorageManager.addToMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemorySubtract -> {
                    memoryStorageManager.subtractFromMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryMultiply -> {
                    memoryStorageManager.multiplyToMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryDivide -> {
                    memoryStorageManager.divideAtMemory(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                // Angle mode
                KeyboardSpecialFunction.AngleMode -> {
                    displayController?.setAngleField(
                        calculatorController?.setSwitchAngleMode()!!
                    )
                }

                // Функция - пропускает действие (если нужно)
                KeyboardSpecialFunction.Skip -> {
                    Log.d(LogTags.LOG_MEDIATOR_CONTROLLER, "The special function is skipped")
                }

                else -> KeyboardSpecialFunction.Skip
            }

            mediatorResultController.handleOnClickSpecialFunction(function)
        }
        catch (ex: NullPointerException) {
            Log.e(LogTags.LOG_MEDIATOR_CONTROLLER, "The special function is skipped")
        }
    }

    override fun isResultCondition(onCalculatedResult: ((condition: Boolean) -> Unit?)?) {
    }

    /** При нажатии на элемент "Истории" - устанавливает в калькулятор выбраное выражение. Возвращает выбраное выражение */
    override fun onHistoryItemClicked(expression: String): String {
        calculatorController?.setExpression(expression)
        updateDisplayExpression()
        return expression
    }

    private fun addToExpression(newSymbol: String) {
        calculatorController?.let {
            val newExpression = it.addToExpression(newSymbol)
            displayController?.setExpressionField(newExpression)
        }
    }

    /** Обновление дисплея */
    private fun updateDisplayExpression() {
        displayController?.setExpressionField(
            calculatorController?.getUserExpression()?.getExpression()!!
        )
    }

    /** Обновляет локальные поля */
    private fun updateCalculationFields(): Pair<String, String> {
        var userExpression = ""
        var calculatedResult = ""

        calculatorController?.let {
            userExpression = it.getUserExpression().getExpression()
            calculatedResult = it.calculateExpression()
        }
        return Pair(userExpression, calculatedResult)
    }

    /** Добавление в историю нового элемента */
    private fun makeHistoryRecordFeature(calculationData: Pair<String, String>) {
        historyService?.addHistoryItem(
            History(
                null,
                calculationData.first,
                calculationData.second,
                calendarService.getUnixTime()
            )
        )
    }

    /** Сохранение результата в память */
    private fun saveResultToMemoryFeature() {
        val condition = SettingsSingleton.getPreferenceCondition(OptionName.AutoSaveMemory.name, false)
        if (condition) handleOnClickSpecialFunction(KeyboardSpecialFunction.MemorySave)
    }

    fun getLastExpression() {
        val condition = SettingsSingleton.getPreferenceCondition(OptionName.KeepLastRecord.name, false)
        if (condition) {
            val gotExpression = SettingsSingleton.getPreferenceCondition(PreferencesKeys.KEY_LAST_EXPRESSION, "")
            calculatorController?.setExpression(gotExpression)
        }
    }

}

