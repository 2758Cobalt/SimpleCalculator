package com.cobaltumapps.simplecalculator.v15.calculator.components.mediator

import android.util.Log
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardArifmeticOperation
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialFunction
import com.cobaltumapps.simplecalculator.data.calculator.enums.KeyboardSpecialOperation
import com.cobaltumapps.simplecalculator.domain.calculator.controller.BaseCalculatorController
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.DisplayExpressionController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.EngineeringController
import com.cobaltumapps.simplecalculator.v15.calculator.components.keyboard.controllers.NumpadController
import com.cobaltumapps.simplecalculator.v15.calculator.components.settings.SettingsSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.preferences.data.OptionName
import com.cobaltumapps.simplecalculator.v15.calculator.services.datetime_calendar.CalendarService
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.CalculatorHistoryController
import com.cobaltumapps.simplecalculator.v15.calculator.services.history.interfaces.HolderOnClickListener
import com.cobaltumapps.simplecalculator.v15.calculator.services.memory.MemoryStorageControllerSingleton
import com.cobaltumapps.simplecalculator.v15.calculator.services.room.model.History
import com.cobaltumapps.simplecalculator.v15.preferences.PreferencesKeys
import com.cobaltumapps.simplecalculator.v15.references.LogTags

class MediatorController: MediatorClickHandler, HolderOnClickListener {
    private val mediatorResultController = MediatorResultController()

    var calculatorController: BaseCalculatorController? = null

    var displayController: DisplayExpressionController? = null

    var numpadController: NumpadController? = null
    var engNumpadController: EngineeringController? = null

    var historyService: CalculatorHistoryController? = null
    private val memoryStorageManager by lazy { MemoryStorageControllerSingleton.getInstance() }

    private val calendarService = CalendarService()

    override fun handleOnClickNumber(number: Number) {
        mediatorResultController.handleOnClickNumber(number)
        addToExpression(number.toString())
    }

    override fun handleOnClickMathOperation(operation: KeyboardArifmeticOperation) {
        mediatorResultController.handleOnClickMathOperation(operation)
        addToExpression(operation.symbol.toString())
    }

    override fun handleOnClickSpecialOperation(operation: KeyboardSpecialOperation) {
        mediatorResultController.handleOnClickSpecialOperation(operation)
        addToExpression(operation.symbol)
    }

    override fun handleOnClickSpecialFunction(function: KeyboardSpecialFunction) {
        try {
            when(function) {
                KeyboardSpecialFunction.Equal -> {
                    mediatorResultController.isResultCondition { isResult ->
                        if (isResult) {
                            val result = updateCalculationFields()

                            calculatorController?.setNewExpression(result.second)

                            displayController?.setExpressionField(result.second)
                            displayController?.clearResultField()
                        }
                        else {
                            val result = updateCalculationFields()

                            displayController?.setResultField(result.second)
                            makeHistoryRecordFeature(result)
                            saveResultToMemoryFeature()
                        }

                    }
                }

                KeyboardSpecialFunction.Backspace -> {
                    calculatorController?.removeLastSymbolExpression()
                    displayController?.setExpressionField(calculatorController?.getExpression()!!)
                    displayController?.clearResultField()
                }

                KeyboardSpecialFunction.AllClear -> {
                    displayController?.allClearFields()
                    calculatorController?.clearExpression()
                }

                KeyboardSpecialFunction.GroupDigitsCleaning -> {
                    calculatorController?.removeDigitsFromEnd()
                    displayController?.setExpressionField(calculatorController?.getExpression()!!)
                }

                KeyboardSpecialFunction.Invert -> {
                    displayController?.setExpressionField(calculatorController?.closeExpressionString()!!)
                }

                KeyboardSpecialFunction.MemorySave -> {
                    memoryStorageManager.saveMemoryValue(calculatorController?.getCalculatedExpression()!!) { result -> displayController?.setMemoryField(result) }
                }

                KeyboardSpecialFunction.MemoryRead -> {
                    calculatorController?.setNewExpression(memoryStorageManager.readMemory().toString())
                    updateDisplayExpression()
                }

                KeyboardSpecialFunction.MemoryClear -> {
                    memoryStorageManager.clearMemory { result -> displayController?.setMemoryField(result) }
                }

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

                KeyboardSpecialFunction.AngleMode -> {
                    displayController?.setAngleField(
                        calculatorController?.setSwitchAngleMode()!!
                    )
                }

                KeyboardSpecialFunction.Skip -> {
                    Log.i(LogTags.LOG_MEDIATOR_CONTROLLER, "The special function is skipped")
                }

                else -> KeyboardSpecialFunction.Skip
            }

            mediatorResultController.handleOnClickSpecialFunction(function)
        }
        catch (ex: NullPointerException) {
            Log.e(LogTags.LOG_MEDIATOR_CONTROLLER, "The special function is skipped.\nERROR:${ex.localizedMessage}")
        }
    }

    /** При нажатии на элемент "Истории" - устанавливает в калькулятор выбраное выражение. Возвращает выбраное выражение */
    override fun onHistoryItemClicked(expression: String): String {
        calculatorController?.setNewExpression(expression)
        updateDisplayExpression()
        return expression
    }

    private fun addToExpression(newSymbol: String) {
        calculatorController?.let {
            val newExpression = it.addSymbol(newSymbol)
            displayController?.setExpressionField(newExpression)
        }
    }

    /** Обновление дисплея */
    private fun updateDisplayExpression() {
        displayController?.setExpressionField(
            calculatorController?.getExpression()!!
        )
    }

    /** Обновляет локальные поля */
    private fun updateCalculationFields(): Pair<String, String> {
        var userExpression = ""
        var calculatedResult = ""

        calculatorController?.let {
            userExpression = it.getExpression()
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
            calculatorController?.setNewExpression(gotExpression)
        }
    }

}