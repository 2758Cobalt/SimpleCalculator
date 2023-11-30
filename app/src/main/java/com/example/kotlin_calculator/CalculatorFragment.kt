package com.example.kotlin_calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Locale
import kotlin.math.log

class CalculatorFragment: Fragment() {
    private val constantSymbols: Array<String> = arrayOf("+", "-", "*", "/", "=",".") // constraints

    private val numbers = mutableListOf(0.0) // Числовой массив
    private val operators = mutableListOf<String>() // Массив операторов
    private var calculateResult: Double = 0.0 // Результат подсчёта
    private var isResult: Boolean = false //
    private var resetOperator: Boolean = false //
    private lateinit var container: FrameLayout

    private lateinit var resultText: TextView // Поле с результатом
    private lateinit var historyText: TextView // Поле истории (отображает ранее введённые числа/операторы)
    private lateinit var operatorText: TextView // Поле с значком оператора

    private lateinit var roundButton: Button
    private lateinit var clearAllButton: Button
    private lateinit var percentButton: Button
    private lateinit var resultButton: Button
    private lateinit var pointButton: Button
    private lateinit var backSpaceButton: Button

    private lateinit var operatorAdd: Button
    private lateinit var operatorSub: Button
    private lateinit var operatorMul: Button
    private lateinit var operatorDiv: Button

    private var canEnterOperation: Boolean = false
    private var canEnterNumber: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false) // Сам view фрагмента

        // Ссылка на текстовые поля
        resultText = view.findViewById(R.id.resultField)
        historyText = view.findViewById(R.id.historyField)
        operatorText = view.findViewById(R.id.operatorField)

        // Ссылка на кнопки функций калькулятора
        clearAllButton = view.findViewById(R.id.ButtonClearAll)// Ссылка на кнопку "AC"
        percentButton = view.findViewById(R.id.buttonPercent)// Ссылка на кнопку "%"
        roundButton = view.findViewById(R.id.buttonRound) // Ссылка на кнопку "round"
        resultButton = view.findViewById(R.id.buttonResult) // Ссылка на кнопку "round"
        pointButton = view.findViewById(R.id.buttonPoint) // Ссылка на кнопку "round"
        backSpaceButton = view.findViewById(R.id.buttonBackSpace) // Ссылка на кнопку "round"

        // Ссылка на кнопки операторов
        operatorAdd = view.findViewById(R.id.addBtn)
        operatorSub = view.findViewById(R.id.subBtn)
        operatorMul = view.findViewById(R.id.mulBtn)
        operatorDiv = view.findViewById(R.id.divBtn)

        // Ссылка на Numpad
        val btn0: Button = view.findViewById(R.id.num0)
        val btn1: Button = view.findViewById(R.id.num1)
        val btn2: Button = view.findViewById(R.id.num2)
        val btn3: Button = view.findViewById(R.id.num3)

        val btn4: Button = view.findViewById(R.id.num4)
        val btn5: Button = view.findViewById(R.id.num5)
        val btn6: Button = view.findViewById(R.id.num6)

        val btn7: Button = view.findViewById(R.id.num7)
        val btn8: Button = view.findViewById(R.id.num8)
        val btn9: Button = view.findViewById(R.id.num9)

        val numberButtonIds = arrayOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9) // Кнопки (0 1 2 3...9)
        val operatorButtons = arrayOf(operatorAdd, operatorSub, operatorMul, operatorDiv) // Кнопки операторы (+ - * /)

        for (button in numberButtonIds) {
            // Слушатель нажатий для всего нампада(numpad)
            button.setOnClickListener { numberEnterAction(it) }
        }

        for (operatorButton in operatorButtons) {
            // Слушатель нажатий кнопок операторов
            operatorButton.setOnClickListener { operationEnterAction(it) }
        }

        clearAllButton.setOnClickListener { clearAllAction() }
        roundButton.setOnClickListener { roundAction() }
        percentButton.setOnClickListener { percentResult() }
        resultButton.setOnClickListener { equalAction() }
        pointButton.setOnClickListener { enterPointAction() }
        backSpaceButton.setOnClickListener { backSpaceAction() }


        return view
    }

    private fun numberEnterAction(view: View) // Обработчик нажатия - цифра (0-9)
    {
        if(isResult){
            clearAllFields(resultField = true, historyField = true, operatorField = false)
            isResult = false
            canEnterNumber = true
        }

        if (view is Button && canEnterNumber) {
            if (resultText.text == "0")
                resultText.text = ""

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

        isResult = false
    }
    private fun arrayNumberAdd(){
        numbers[numbers.lastIndex] =
            resultText.text.toString().replace(Regex("[+\\-*/]"), "").toDouble()
    }

    private fun operationEnterAction(view: View) // Обработчик нажатия - оператора (+ - * /)
    {
        // Замена уже введеного оператора
        if(!resetOperator){
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

        // Ввод оператора
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

    private fun calculateAction(): Double {
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
        if (!isResult && resultText.text.isNotEmpty()) {
            /*Принцип работы
            * 1. Пререводим в mutableList для получения каждого символа строки
            * 2. Удаляем последний символ в обоих текстовых полях
            * 4. mutableList переводим обратно в строку и присваиваем в текстовые поля
            * 4. Проверяем, что result не пустой(history аналогично result) и тогда меняем старое число на новое в массиве numb
            * Заполняем строку оставшимся символом
            * (Для history принцип аналогичный)*/

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
                numbers[numbers.lastIndex] = resultText.text.toString().toDouble()
            }

        }
    }


    private fun equalAction() // Обработчик нажатия - равно(=)
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
        isResult = true

    }

    private fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        // Очистка массивов
        numbers.clear()
        operators.clear()

        numbers.add(0.0)

        // Очистка текстовых полей
        clearAllFields(resultField = true, historyField = true, operatorField = true)

        canEnterOperation = false
        canEnterNumber = true
    }

    private fun roundAction(roundRange: Int = 1) {
        // Округление результата до десятых
        val result = resultText.text.toString().toDouble()
        resultText.text = String.format(Locale.US, "%.${roundRange}f", result)
    }

    private fun percentResult() {
        // Расчёт процента результата
        val result = resultText.text.toString().toDouble()
        resultText.text = (result / 100.0).toString()
    }

    private fun clearAllFields(resultField: Boolean, historyField: Boolean, operatorField: Boolean) {

        if(resultField) resultText.text = ""; resultText.hint = "0"
        if(historyField) historyText.text = ""; historyText.hint = "0"
        if(operatorField) operatorText.text = ""

    }
    private fun clearTextFields(){
        // Очищает только чисельные поля
        clearAllFields(resultField = true, historyField = true, operatorField = false)
    }

}