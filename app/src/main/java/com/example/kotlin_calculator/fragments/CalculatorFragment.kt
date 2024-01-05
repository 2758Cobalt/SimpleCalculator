package com.example.kotlin_calculator.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.activities.HistoryActivity
import com.example.kotlin_calculator.activities.OptionsActivity
import com.example.kotlin_calculator.references.CalculatorCore
import com.example.kotlin_calculator.references.ConstantsCalculator
import com.example.kotlin_calculator.references.Memory
import java.util.Locale


class CalculatorFragment: Fragment() {
    private val memory: Memory by lazy { Memory() }                                            // Объект памяти
    private var calculateResult: Double = 0.0                                   // Результат подсчёта

    // Настройки
    private var buttonBoxSize = 200                                            // Experimental

    // Логика
    private var canEnterOperand = true                                          // Возможность вводить операнд
    private var canEnterOperation = true                                        // Возможность вводить операцию
    private var canPowerEnter = true                                            // Вохзможность вводить знак возведения в степень
    private var canSqrtEnter = true                                             // Возможность вводить квадратный корень
    private var canSwapOperation = false                                        // Возможность заменить оператор
    private var memoryAutoSaveResult = false                                    // Автосохранение результата вычислений в память

    // Массивы с кнопками
    private var numberButtons: Array<Button> = arrayOf()                         // Массив с ссылками кнопок цифр
    private var operatorButtons: Array<Button> = arrayOf()                      // Массив с ссылками кнопок операторов
    private var functionalButtons: Array<Button> = arrayOf()                    // Массив с фукциями калькулятора
    private var additionalButtons: Array<Button> = arrayOf()                    // Массив с допольнительными кнопками для панели быстрого доступа
    private var quickContent: Array<Button> = arrayOf()                         // Массив, в котором хранятся кнопки панели быстрого доступа
    private var historyExpressions: Array<String> = arrayOf()                   // Массив, хранящий историю

    // Текстовые поля
    private lateinit var inputExpression: TextView                              // Поле с результатом
    private lateinit var resultExpression: TextView                             // Поле истории (отображает ранее введённые числа/операторы)
    private lateinit var memoryTextView: TextView                               // Поле отображающее данных в памяти

    private lateinit var quickContainer: LinearLayout

    // Кнопки "иконки"
    private lateinit var optionsIcon: ImageView                                 // Открывает настройки
    private lateinit var historyIcon: ImageView                                 // Открывает историю

    private var fullCalculatorExpression: String = ""                           // Строка в которую записывается история (полное выражение)
    private var expression: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false) // View фрагмента

        // Ссылка на текстовые поля
        inputExpression = view.findViewById(R.id.resultField)             // Ссылка на тектовое поле текущей записи
        resultExpression = view.findViewById(R.id.historyField)           // Ссылка на тектовое поле истории (полной записи)
        memoryTextView = view.findViewById(R.id.memoryField)                    // Ссылка на тектовое поле памяти

        quickContainer = view.findViewById(R.id.quickToolBarContent)

        optionsIcon = view.findViewById(R.id.buttonOptions)                     // Ссылка на кнопку "options"
        historyIcon = view.findViewById(R.id.buttonHistory)                     // Ссылка на кнопку "history"


        // Создание и запись ссылок в массив
        numberButtons = ConstantsCalculator.numberButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()
        operatorButtons =  ConstantsCalculator.operatorButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()
        functionalButtons =  ConstantsCalculator.functionalButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()
        additionalButtons =  ConstantsCalculator.additionalButtonIds.map { view.findViewById<Button>(it) }.toTypedArray()

        quickContent = arrayOf(
            additionalButtons[0],
            additionalButtons[1],
            additionalButtons[2],
            additionalButtons[3],
            functionalButtons[8],
            functionalButtons[9]
        )
        fillQuickAccessToolbar(quickContent,quickContainer)

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операндами
        for ((index,numberButton) in numberButtons.withIndex()) {
            numberButton.setOnClickListener { numberEnterAction(ConstantsCalculator.operandConstants[index]) } // Слушатель нажатий для всего нампада(numpad)
            setButtonParams(numberButton,buttonBoxSize)
        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с операцией
        for ((index,operatorButton) in operatorButtons.withIndex()) {
            operatorButton.setOnClickListener { operationEnterAction(ConstantsCalculator.operatorConstants[index]) } // Слушатель нажатий кнопок операторов
            setButtonParams(operatorButton,buttonBoxSize)
        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам с доп функциями
        for (functionalButton in functionalButtons) {
            setButtonParams(functionalButton, buttonBoxSize)
        }

        // Присвоение слушателя нажатий (OnClickListener) кнопкам в панели быстрого доступа
        for (quickBtn in quickContent) {
            setButtonParams(quickBtn, buttonBoxSize - 50)
        }

        // Слушатель нажатий кнопок функций
        functionalButtons[0].setOnClickListener { clearAllAction() }
        functionalButtons[1].setOnClickListener { roundAction() }
        functionalButtons[2].setOnClickListener { equalAction() }
        functionalButtons[3].setOnClickListener { enterPointAction() }
        functionalButtons[4].setOnClickListener { backSpaceAction() }
        functionalButtons[8].setOnClickListener { powerAction() }
        functionalButtons[9].setOnClickListener { sqrtAction() }

        // Скобки

        // Открывает скобку
        functionalButtons[5].setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[6]) }

        // Закрывает скобку
        functionalButtons[6].setOnClickListener { numberEnterAction(ConstantsCalculator.operatorConstants[7]) }

        // Иконки настроек и истории
        optionsIcon.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java)) } // Option Icon
        historyIcon.setOnClickListener { startActivity(Intent(requireContext(), HistoryActivity::class.java)) } // History Icon

        additionalButtons[0].setOnClickListener { memory.save(expression) }         // MS
        additionalButtons[1].setOnClickListener { memory.read() }                   // MR
        additionalButtons[2].setOnClickListener { memory.clear(); memoryTextView.text = ""  }                  // MC
        additionalButtons[3].setOnClickListener {                            // AMS - Auto Memory Save
            memoryAutoSaveResult = !memoryAutoSaveResult
            if (memoryAutoSaveResult)
                additionalButtons[3].setBackgroundResource(R.drawable.style_button_operator)
            else
                additionalButtons[3].setBackgroundResource(R.drawable.style_button_functional)
        }


        return view
    }

    private fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        if(inputExpression.text.toString() == "0") // Стирает ноль если он стоит первым
            inputExpression.text = ""


        // Если доступен ввод числа
        inputExpression.append(numberSymbol)
        changeAccessVariableInput(canEnterOperand,
            operationEnter = true,
            swapOperator = false,
            powerEnter = true,
            sqrtEnter = true
        )
    }
    private fun operationEnterAction(operator: String, ignoreRechange: Boolean = false) // Обработчик нажатия - оператора (+ - * /)
    {
        if(inputExpression.text == "0")
            inputExpression.text = ""
        if (canEnterOperation){
            inputExpression.append(operator)

            changeAccessVariableInput(canEnterOperand,
                operationEnter = true,
                swapOperator = true,
                powerEnter = canPowerEnter,
                sqrtEnter = canSqrtEnter
            )
        }
        if(canSwapOperation && !ignoreRechange){
            //Замена оператора
            backSpaceAction()
            inputExpression.append(operator)
        }
    }

    private fun sqrtAction() {
        // Вызов квадратного корня
        if(canSqrtEnter){
            canEnterOperation = true
            operationEnterAction("√", true)
            canSqrtEnter = false
        }
    }

    private fun powerAction() {
        // Вызов возведения в степень
        if (canPowerEnter){
            operationEnterAction("^")
            canPowerEnter = false
        }
    }

    private fun enterPointAction() // Ввод точки
    {
        // После ввода - отключает возможность повторно ввести
        if (inputExpression.text.isNotEmpty()) {
            inputExpression.append(ConstantsCalculator.operatorConstants[5])
        }
    }

    // Расчитывает результат и возвращает его
    private fun calculateAction(): Double{
        return CalculatorCore.calculateExpression(inputExpression.text.toString())
    }

    private fun backSpaceAction() {
        try {
            if (inputExpression.text.isNotEmpty()) {

                // Переводим текст поля в список символов (Результат - слово = [с,л,о,в,о]) и записываем в локальные переменные для удобства
                val result = inputExpression.text.toMutableList()

                // Удаляем последний элемент в текстовом поле
                result.removeLast()

                // Переводим список обратно в строку
                inputExpression.text = result.joinToString(separator = "")

            }
        } catch (e: Exception) {
            errorHandler()
        }
    }


    private fun equalAction() // Событие вычислена результата
    {
        try {


            // Расчёт результата
            val result = calculateAction()

            // Запись в основное поле и запись результата в переменную
            calculateResult = result

            resultExpression.text = calculateResult.toString()
            expression = resultExpression.text.toString()


            saveExpressionToHistory()
        }
        catch (_: Exception){
            errorHandler()
        }
    }


    private fun roundAction(roundRange: Int = 1) // Округление результата до десятых
    {
        val roundedResult = resultExpression.text.toString().toDouble()
        resultExpression.text = String.format(Locale.US, "%.${roundRange}f", roundedResult)
    }


    private fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        // Очистка текстовых полей
        clearAllFields(resultField = true, expressionField = true, true)
    }

    private fun changeAccessVariableInput(numberOperand: Boolean = true, operationEnter: Boolean = false, swapOperator: Boolean = false,
                                          powerEnter: Boolean = false, sqrtEnter: Boolean = false){
        canEnterOperand = numberOperand
        canEnterOperation = operationEnter
        canSwapOperation = swapOperator
        canPowerEnter = powerEnter
        canSqrtEnter = sqrtEnter
    }

    private fun clearAllFields(resultField: Boolean, expressionField: Boolean, fullExpression: Boolean) // Очистка всех полей
    {
        if(resultField) inputExpression.text = "0"
        if(expressionField){
            expression = "0"
            resultExpression.text = "0"}
        if(fullExpression) fullCalculatorExpression = ""
    }


    private fun errorHandler() // Обработчик ошибки
    {
        resultExpression.text = "Failed calculation"
    }

    private fun saveExpressionToHistory(){
        historyExpressions += expression

        Log.i("DebugTag","История:")
        for ((index, historyElement) in historyExpressions.withIndex()){
            Log.i("DebugTag","Запись №$index: $historyElement")
        }

    }


    private fun setButtonParams(button: Button, size: Int = 140){
        // Назначает параметры кнопке
        button.layoutParams.width = size
        button.layoutParams.height = size
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, (size / 10) + 4f)
        Log.i("DebugTag",button.textSize.toString())
    }

    private fun fillQuickAccessToolbar(buttonArray: Array<Button>, container: ViewGroup?){
        // Заполняет панель быстрого доступа кнопками
        container?.removeAllViews()
        for(button in buttonArray){
            container?.addView(button)
        }
    }

    private fun buildDialog(title: String, description: String, bindPositive: Runnable){
        // Вызов диалога
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(description)

        builder.setPositiveButton("Yes") { _, _ -> // Действия при нажатии кнопки "Ок"
            bindPositive.run()
        }

        builder.setNegativeButton("No") { dialog, _ ->  // Действия при нажатии кнопки "Отмена"
            dialog.dismiss()
        }

        builder.create().show()  // Создаёт диалог
    }
}