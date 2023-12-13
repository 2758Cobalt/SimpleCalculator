package com.example.kotlin_calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.Locale

class CalculatorFragment: Fragment() {
    private val constantSymbols: Array<String> = arrayOf("+", "-", "*", "/", "=",".") // constraints

    private val numbers = mutableListOf(0.0)                                // Числовой массив
    private val operators = mutableListOf<String>()                         // Массив операторов
    private var calculateResult: Double = 0.0                               // Результат подсчёта

    // Текстовые поля
    private lateinit var resultText: TextView                               // Поле с результатом
    private lateinit var historyText: TextView                              // Поле истории (отображает ранее введённые числа/операторы)
    private lateinit var operatorText: TextView                             // Поле с значком оператора

    // Кнопки Функций (Round, Backspace, PercentOf, Equal...). При нажатии ->
    private lateinit var roundButton: Button                                // Округление числа
    private lateinit var clearAllButton: Button                             // Очистка всех полей и переменных
    private lateinit var percentButton: Button                              // Процент числа
    private lateinit var equalButton: Button                                // Расчёт результата записи
    private lateinit var pointButton: Button                                // Добавление точки
    private lateinit var backSpaceButton: Button                            // Удаление последнего символа

    // Логика
    private var canEnterNumber: Boolean = true                              // Разрешает ввод числа
    private var canEnterOperation: Boolean = false                          // Разрешает ввод операции
    private var canPercentCalculate: Boolean = false                        // Разрешает вычислять процент
    private var resetOperator: Boolean = false                              // Указывает возможность замены оператора
    private var isResult: Boolean = false                                   // Если расчитан результат (нажата equalButton)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false) // View фрагмента

        // Ссылка на текстовые поля
        resultText = view.findViewById(R.id.resultField)                    // Ссылка на тектовое поле текущей записи
        historyText = view.findViewById(R.id.historyField)                  // Ссылка на тектовое поле истории (полной записи)
        operatorText = view.findViewById(R.id.operatorField)                // Ссылка на тектовое поле текущего выбранного оператора

        // Ссылка на кнопки функций калькулятора
        clearAllButton = view.findViewById(R.id.buttonClearAllAction)       // Ссылка на кнопку "AC"
        percentButton = view.findViewById(R.id.buttonPercentAction)         // Ссылка на кнопку "%"
        roundButton = view.findViewById(R.id.buttonRoundAction)             // Ссылка на кнопку "round"
        equalButton = view.findViewById(R.id.buttonEqualAction)             // Ссылка на кнопку "equal"
        pointButton = view.findViewById(R.id.buttonPointAction)             // Ссылка на кнопку "point"
        backSpaceButton = view.findViewById(R.id.buttonBackSpaceAction)     // Ссылка на кнопку "backspace"

        // Создание ссылок на кнопки numpad
        val numberButtonIds = arrayOf(
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
        val operatorButtons = arrayOf(
            view.findViewById(R.id.addBtn) as Button,
            view.findViewById(R.id.subBtn) as Button,
            view.findViewById(R.id.mulBtn) as Button,
            view.findViewById(R.id.divBtn) as Button) // Кнопки операторы (+ - * /)

        // Присвоение слушателя нажатий (OnClickListener) кнопкам
        for (button in numberButtonIds) {
            button.setOnClickListener { numberEnterAction(it) } // Слушатель нажатий для всего нампада(numpad)
        }

        for (operatorButton in operatorButtons) {
            operatorButton.setOnClickListener { operationEnterAction(it) } // Слушатель нажатий кнопок операторов
        }

        // Слушатель нажатий кнопок функций
        clearAllButton.setOnClickListener { clearAllAction() }
        roundButton.setOnClickListener { roundAction() }
        percentButton.setOnClickListener { percentAction()}
        equalButton.setOnClickListener { equalAction() }
        pointButton.setOnClickListener { enterPointAction() }
        backSpaceButton.setOnClickListener { backSpaceAction() }
        return view
    }

    private fun numberEnterAction(view: View) // Обработчик нажатия - цифра (0-9)
    {
        try
        {
            if(isResult){
                clearAllFields(resultField = true, historyField = true, operatorField = false)
                isResult = false
                canEnterNumber = true
            }

            if (view is Button && canEnterNumber) {
                if (resultText.text.toString() == "0") // Срабатывает если первым стоит 0
                    resultText.text = ""

                if (historyText.text.toString() == "0")
                    historyText.text = ""

                // 1. Запись в основное поле
                resultText.append(view.text)

                // 2. Добавление в массив введёного первого числа
                numbers[numbers.lastIndex] =
                    resultText.text.toString().replace(Regex("[+\\-*/]"), "").toDouble()

                // 3. Запись в поле истории
                historyText.append(view.text)
            }

            // Разрешает ввод операции
            canEnterOperation = true

            // Разрешает вычислять процент
            canPercentCalculate = true

            // Выкл. статус результата
            isResult = false
        }
        catch (e: Exception)
        {
            errorHandler()
        }

    }

    private fun arrayNumberAdd(){
        numbers[numbers.lastIndex] =
            resultText.text.toString().replace(Regex("[+\\-*/]"), "").toDouble()
    }

    private fun operationEnterAction(view: View) // Обработчик нажатия - оператора (+ - * /)
    {
        try {
            if(!resetOperator) // Замена уже введеного оператора, если он есть
            {
                arrayNumberAdd()
            }
            if (view is Button && resetOperator) // Выбор операции
            {
                if(operatorText.text.isNotEmpty())
                {
                    operators[operators.lastIndex] = view.text.toString()
                    operatorText.text = view.text
                }
            }

            if(canEnterOperation){

                numbers.add(0.0) // Добавление в массив введёного пустого числа
                operators.add("") //  Добавление в массив введёную операцию

                // Очистка основного поля
                clearAllFields(resultField = true, historyField = false, operatorField = false)


                if (view is Button && canEnterOperation) // Выбор операции
                {
                    if (!canEnterNumber) {
                        historyText.text = ""
                        historyText.append(calculateResult.toString())
                    }
                    // 1. Запись в operator поле
                    operatorText.text = view.text

                    // 2. Добавление в массив введёной операции
                    operators[operators.lastIndex] = operatorText.text.toString()

                    // 3. Запись в поле истории
                    historyText.append(view.text)
                }
                // Блок ввода операции (воизбежании повторного ввода)
                canEnterOperation = false

                // Разрешает ввод чисел
                canEnterNumber = true

                // Разрешает замену оператора
                resetOperator = true

                // Отключает состояние результата
                isResult = false
            }
        }
        catch (e:Exception)
        {
            errorHandler()
        }
    }

    private fun enterPointAction() // Ввод точки
    {
        // После ввода - отключает возможность повторно ввести
        if (resultText.text.isNotEmpty()) {
            if (!resultText.text.contains(constantSymbols[5])) {
                resultText.append(constantSymbols[5])
                historyText.append(constantSymbols[5])
            }
        }
    }

    private fun calculateAction(): Double // Расчёт
    {
        var result = numbers[0]

        for (i in 1 until numbers.size) {
            // Получаем операцию из второго массива
            val operation = operators[i - 1]

            // Получаем следующее число из первого массива
            val nextNumber = numbers[i]

            // Выполняем операцию в зависимости от оператора
            when (operation) {
                constantSymbols[0] -> result += nextNumber
                constantSymbols[1] -> result -= nextNumber
                constantSymbols[2] -> result *= nextNumber
                constantSymbols[3] -> result /= nextNumber
            }
        }

        // Очистка масивов
        numbers.clear()
        operators.clear()
        numbers.add(result)

        return result
    }

    private fun backSpaceAction() {
        /*Принцип работы
            * 1. Пререводим в mutableList для получения каждого символа строки
            * 2. Удаляем последний символ в обоих текстовых полях
            * 4. mutableList переводим обратно в строку и присваиваем в текстовые поля
            * 4. Проверяем, что result не пустой(history аналогично result) и тогда меняем старое число на новое в массиве numb
            * Заполняем строку оставшимся символом
            * (Для history принцип аналогичный)*/
        try{
            if (!isResult && resultText.text.isNotEmpty()) {

                // Переводим текст полей в спиоск (Результат - слово = [с,л,о,в,о]) и записываем в локальные переменные для удобства
                val result = resultText.text.toMutableList()
                val history = historyText.text.toMutableList()

                // Удаляем последний элемент в обоих текстовых полях
                result.removeLast()
                history.removeLast()

                // Переводим список обратно в строку
                resultText.text = result.joinToString(separator = "")
                historyText.text = history.joinToString(separator = "")

                if (result.isNotEmpty()) {
                    // Заменяем последнее ввёденое число
                    debugData()
                    numbers[numbers.lastIndex] = resultText.text.toString().toDouble()
                }
            }
        }catch (e: Exception){
            errorHandler()
        }

    }

    private fun equalAction() // Обработчик нажатия - равно (=)
    {
        // Расчёт результата
        val result = calculateAction()

        // Очистка поля оператора
        clearAllFields(resultField = false, historyField = false, operatorField = true)

        // Запись в основное поле и запись результата в переменную
        resultText.text = result.toString()
        calculateResult = result

        // Блокировка повторного ввода числа
        canEnterNumber = false

        // Назначение статуса результата (когда нажато действие "=")
        isResult = true

        setStyleView(clearAllButton, R.drawable.style_button_operator)
    }

    private fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        // Очистка массивов
        numbers.clear()
        operators.clear()
        numbers.add(0.0)

        // Очистка текстовых полей
        clearAllFields(resultField = true, historyField = true, operatorField = true)

        // Сброс переменных
        resetAllVariables(
            enterNumber = true,
            enterOperator = false,
            enterPersent = false,
            operatorReset = false,
            result = false
        )

        setStyleView(clearAllButton, R.drawable.style_button_functional)

    }

    private fun roundAction(roundRange: Int = 1) // Округление результата до десятых
    {
        val result = resultText.text.toString().toDouble()
        resultText.text = String.format(Locale.US, "%.${roundRange}f", result)
    }

    private fun percentAction() // Расчёт процента результата
    {
        try {
            if(canPercentCalculate)
            {
                val result = resultText.text.toString().toDouble()
                resultText.text = (result / 100.0).toString()
            }
        }catch (e:Exception){

        }

    }

    private fun clearAllFields(resultField: Boolean, historyField: Boolean, operatorField: Boolean) // Очистка всех полей
    {
        if(resultField) resultText.text = ""; resultText.hint = "0"
        if(historyField) historyText.text = ""; historyText.hint = "0"
        if(operatorField) operatorText.text = ""

    }

    private fun resetAllVariables(enterNumber: Boolean,enterOperator: Boolean,enterPersent: Boolean,operatorReset: Boolean,result: Boolean){
        canEnterNumber = enterNumber
        canEnterOperation = enterOperator
        canPercentCalculate = enterPersent
        resetOperator = operatorReset
        isResult = result
        Log.d("DebugTag","All boolean variables has been reset")
    }

    private fun setStyleView(viewElement: View, resId: Int) // Задаёт стиль view-элементу
    {
        viewElement.setBackgroundResource(resId)
    }

    private fun debugData(){
        val tagDebug = "DebugTag"
        Log.i(tagDebug, "Array of Numbers: $numbers")
        Log.i(tagDebug, "Array of Operators: $operators")

        Log.i(tagDebug, "All booleans: \"canEnterNumber\": $canEnterNumber\n" +
                "\"canEnterOperation\": $canEnterOperation" +
                "\"resetOperator\": $resetOperator" +
                "\"isResult\": $isResult")
    }

    private fun errorHandler() // Обработчик ошибки
    {
        setStyleView(clearAllButton, R.drawable.style_button_operator)
        resultText.text = "NaN"
        historyText.text = "Error"
        resetAllVariables(
            enterNumber = true,
            enterOperator = false,
            enterPersent = false,
            operatorReset = false,
            result = true
        )
    }
}