package com.example.kotlin_calculator

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.Locale

class CalculatorFragment: Fragment() {
    private val operatorConstants: Array<String> = arrayOf("+", "-", "*", "/", "=",".","(",")") // Символы для операций
    private val operandConstants: Array<String> = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")  // Символы для кнопок
    private var calculateResult: Double = 0.0                                   // Результат подсчёта

    // Текстовые поля
    private lateinit var currentNumberTextView: TextView                        // Поле с результатом
    private lateinit var fullExpressionTextView: TextView                       // Поле истории (отображает ранее введённые числа/операторы)
    private lateinit var memoryTextView: TextView                               // Поле отображающее данных в памяти

    private var numberButtonIds: Array<Button> = arrayOf()
    private var operatorButtons: Array<Button> = arrayOf()

    // Кнопки Функций (Round, Backspace, PercentOf, Equal...). При нажатии ->
    private lateinit var clearAllButton: Button                                 // Очистка всех полей и переменных
    private lateinit var roundButton: Button                                    // Округление числа
    private lateinit var percentButton: Button                                  // Процент числа
    private lateinit var equalButton: Button                                    // Расчёт результата записи
    private lateinit var pointButton: Button                                    // Добавление точки
    private lateinit var backSpaceButton: Button                                // Удаление последнего символа
    private lateinit var openBracketButton: Button                              // (
    private lateinit var closeBracketButton: Button                             // )
    private lateinit var optionsIcon: ImageView                                 // Открывает настройки
    private lateinit var historyIcon: ImageView                                 // Открывает историю

    // Логика
    private var canPercentCalculate = false
    private var isResultStatus = false


    private var historyExpressions: Array<String> = arrayOf()                   // Массив, хранящий историю
    private var fullCalculatorExpression: String = ""                           // Строка в которую записывается история(полный пример)
    private var memoryStorage: String = ""                                      // Строка, которая управляется
    private var expression: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false) // View фрагмента

        // Ссылка на текстовые поля
        currentNumberTextView = view.findViewById(R.id.resultField)             // Ссылка на тектовое поле текущей записи
        fullExpressionTextView = view.findViewById(R.id.historyField)           // Ссылка на тектовое поле истории (полной записи)
        memoryTextView = view.findViewById(R.id.memoryField)                    // Ссылка на тектовое поле памяти

        // Ссылка на кнопки функций калькулятора
        clearAllButton = view.findViewById(R.id.buttonClearAllAction)           // Ссылка на кнопку "AC"
        percentButton = view.findViewById(R.id.buttonPercentAction)             // Ссылка на кнопку "%"
        roundButton = view.findViewById(R.id.buttonRoundAction)                 // Ссылка на кнопку "round"
        equalButton = view.findViewById(R.id.buttonEqualAction)                 // Ссылка на кнопку "equal"
        pointButton = view.findViewById(R.id.buttonPointAction)                 // Ссылка на кнопку "point"
        backSpaceButton = view.findViewById(R.id.buttonBackSpaceAction)         // Ссылка на кнопку "backspace"
        optionsIcon = view.findViewById(R.id.buttonOptions)                     // Ссылка на кнопку "options"
        historyIcon = view.findViewById(R.id.buttonHistory)                     // Ссылка на кнопку "history"

        openBracketButton = view.findViewById(R.id.buttonRoundBracketOpen)      // Ссылка на кнопку "history"
        closeBracketButton = view.findViewById(R.id.buttonRoundBracketClose)    // Ссылка на кнопку "history"

        val memoryAdd = view.findViewById(R.id.buttonMemoryAdd) as Button
        val memoryRead = view.findViewById(R.id.buttonMemoryRead) as Button
        val memoryClear = view.findViewById(R.id.buttonMemoryClear) as Button

        // Создание ссылок на кнопки numpad
        numberButtonIds = arrayOf(
            view.findViewById(R.id.num0) as Button,
            view.findViewById(R.id.num1) as Button,
            view.findViewById(R.id.num2) as Button,
            view.findViewById(R.id.num3) as Button,
            view.findViewById(R.id.num4) as Button,
            view.findViewById(R.id.num5) as Button,
            view.findViewById(R.id.num6) as Button,
            view.findViewById(R.id.num7) as Button,
            view.findViewById(R.id.num8) as Button,
            view.findViewById(R.id.num9) as Button)  // Кнопки (0 1 2 3...9)

        // Создание ссылок на кнопки операторов
        operatorButtons = arrayOf(
            view.findViewById(R.id.addBtn) as Button,
            view.findViewById(R.id.subBtn) as Button,
            view.findViewById(R.id.mulBtn) as Button,
            view.findViewById(R.id.divBtn) as Button) // Кнопки операторы (+ - * /)


        // Присвоение слушателя нажатий (OnClickListener) кнопкам
        for ((index,button) in numberButtonIds.withIndex()) {
            button.setOnClickListener { numberEnterAction(operandConstants[index]) } // Слушатель нажатий для всего нампада(numpad)
        }

        for ((index,operatorButton) in operatorButtons.withIndex()) {
            operatorButton.setOnClickListener { operationEnterAction(operatorConstants[index]) } // Слушатель нажатий кнопок операторов
        }

        // Слушатель нажатий кнопок функций
        clearAllButton.setOnClickListener { clearAllAction() }
        roundButton.setOnClickListener { roundAction() }
        percentButton.setOnClickListener { percentAction()}
        equalButton.setOnClickListener { equalAction() }
        pointButton.setOnClickListener { enterPointAction() }
        backSpaceButton.setOnClickListener { backSpaceAction() }
        optionsIcon.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java)) }
        historyIcon.setOnClickListener { startActivity(Intent(requireContext(), HistoryActivity::class.java)) }

        openBracketButton.setOnClickListener { numberEnterAction(operatorConstants[6]) }
        closeBracketButton.setOnClickListener { numberEnterAction(operatorConstants[7]) }


        memoryAdd.setOnClickListener { saveToMemoryAction() }
        memoryRead.setOnClickListener { readFromMemoryAction() }
        memoryClear.setOnClickListener { clearMemoryAction() }

        return view
    }

    private fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        if(currentNumberTextView.text.toString() == "0") // Стирает ноль если он стоит первым
            currentNumberTextView.text = ""

        // Если доступен ввод числа
        currentNumberTextView.append(numberSymbol)

        isResultStatus = false

    }

    private fun operationEnterAction(operator: String) // Обработчик нажатия - оператора (+ - * /)
    {
        currentNumberTextView.append(operator)
        isResultStatus = false

    }

    private fun enterPointAction() // Ввод точки
    {
        // После ввода - отключает возможность повторно ввести
        if (currentNumberTextView.text.isNotEmpty()) {
            if (!currentNumberTextView.text.contains(operatorConstants[5])) {
                currentNumberTextView.append(operatorConstants[5])
            }
        }
    }


    // Расчитывает результат и возвращает его
    private fun calculateAction(): Double{ return CalculatorCore.calculateExpression(currentNumberTextView.text.toString()) }

    private fun backSpaceAction() {
        try{
            if (currentNumberTextView.text.isNotEmpty()) {

                // Переводим текст полей в спиоск (Результат - слово = [с,л,о,в,о]) и записываем в локальные переменные для удобства
                val result = currentNumberTextView.text.toMutableList()

                // Удаляем последний элемент в текстовом поле
                result.removeLast()

                // Переводим список обратно в строку
                currentNumberTextView.text = result.joinToString(separator = "")

            }
        }catch (e: Exception){
            errorHandler()
        }

    }

    private fun equalAction() // Событие вычислена результата
    {
        isResultStatus = true

        // Расчёт результата
        val result = calculateAction()

        // Запись в основное поле и запись результата в переменную
        calculateResult = result
        fullExpressionTextView.text = calculateResult.toString()
        expression = fullExpressionTextView.text.toString()


        setStyleView(clearAllButton, R.drawable.style_button_operator)
        saveExpressionToHistory()
    }



    private fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        // Очистка текстовых полей
        clearAllFields(resultField = true, expressionField = true, true)

        setStyleView(clearAllButton, R.drawable.style_button_functional)

    }

    private fun roundAction(roundRange: Int = 1) // Округление результата до десятых
    {
        val roundedResult = currentNumberTextView.text.toString().toDouble()
        currentNumberTextView.text = String.format(Locale.US, "%.${roundRange}f", roundedResult)
    }

    private fun percentAction() // Расчёт процента результата
    {
        try {
            if(canPercentCalculate) {
                val result = currentNumberTextView.text.toString().toDouble()
                currentNumberTextView.text = (result / 100.0).toString()
            }
        }catch (_:Exception){
        }
    }

    private fun clearAllFields(resultField: Boolean, expressionField: Boolean, fullExpression: Boolean) // Очистка всех полей
    {
        if(resultField) currentNumberTextView.text = "0"
        if(expressionField){
            expression = "0"
            fullExpressionTextView.text = "0"}
        if(fullExpression) fullCalculatorExpression = ""

    }

    private fun setStyleView(viewElement: View, resId: Int) // Задаёт стиль view-элементу
    {
        viewElement.setBackgroundResource(resId)
    }


    private fun errorHandler() // Обработчик ошибки
    {
        setStyleView(clearAllButton, R.drawable.style_button_operator)

        currentNumberTextView.text = "NaN"
        fullExpressionTextView.text = "Error"
    }

    private fun saveExpressionToHistory(){
        val savedHistory = expression
        historyExpressions += savedHistory

        Log.i("DebugTag","История:")
        for ((index, historyElement) in historyExpressions.withIndex()){
            Log.i("DebugTag","Запись №$index: $historyElement")
        }

    }
    private fun saveToMemoryAction() // Сохранение/Перезапись введёного числа в память
    {
        memoryStorage = fullExpressionTextView.text.toString() // Запись в память
        memoryTextView.text = "M: $memoryStorage" // Вывод записаной памяти
    }
    private fun readFromMemoryAction() // Добавляет запись из памяти в строку
    {
        if(memoryStorage.isNotEmpty()){
            for (x in memoryStorage){
                numberEnterAction(x.toString())
            }
        }
    }
    private fun clearMemoryAction() // Очищает память
    {
        memoryStorage = ""
        memoryTextView.text = ""
    }
}