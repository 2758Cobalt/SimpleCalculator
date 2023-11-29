package com.example.kotlin_calculator


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculatorFragment: Fragment() {
    private val constantSymbols: Array<String> = arrayOf("+", "-", "*", "/", "=") // constraints

    private val numbers = mutableListOf(0.0)
    private val operators = mutableListOf<String>()
    private var calculateResult: Double = 0.0

    private lateinit var resultText: TextView // Поле с результатом
    private lateinit var historyText: TextView // Поле истории (отображает ранее введённые числа/операторы)

    private lateinit var operatorText: TextView

    private lateinit var roundButton: Button
    private lateinit var clearAllButton: Button
    private lateinit var percentButton: Button
    private lateinit var resultButton: Button
    private lateinit var pointButton: Button

    private lateinit var operatorAdd: Button
    private lateinit var operatorSub: Button
    private lateinit var operatorMul: Button
    private lateinit var operatorDiv: Button

    private var canEnterOperation: Boolean = false
    private var canEnterNumber: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_calculator, container, false) // Сам view фрагмента

        resultText = view.findViewById(R.id.resultField)
        historyText = view.findViewById(R.id.historyField)
        operatorText = view.findViewById(R.id.operatorField)

        // Ссылка на кнопки функций калькулятора
        clearAllButton = view.findViewById(R.id.ButtonClearAll)// Ссылка на кнопку "AC"
        percentButton = view.findViewById(R.id.buttonPercent)// Ссылка на кнопку "%"
        roundButton = view.findViewById(R.id.buttonRound) // Ссылка на кнопку "round"
        resultButton = view.findViewById(R.id.buttonResult) // Ссылка на кнопку "round"
        pointButton = view.findViewById(R.id.buttonPoint) // Ссылка на кнопку "round"

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

        // Слушатель нажатий Numpad
        btn0.setOnClickListener { numberEnter(it) }
        btn1.setOnClickListener { numberEnter(it) }
        btn2.setOnClickListener { numberEnter(it) }
        btn3.setOnClickListener { numberEnter(it) }

        btn4.setOnClickListener { numberEnter(it) }
        btn5.setOnClickListener { numberEnter(it) }
        btn6.setOnClickListener { numberEnter(it) }

        btn7.setOnClickListener { numberEnter(it) }
        btn8.setOnClickListener { numberEnter(it) }
        btn9.setOnClickListener { numberEnter(it) }

        // Слушатель нажатий кнопок операторов
        operatorAdd.setOnClickListener { operationEnter(it) }
        operatorSub.setOnClickListener { operationEnter(it) }
        operatorMul.setOnClickListener { operationEnter(it) }
        operatorDiv.setOnClickListener { operationEnter(it) }

        clearAllButton.setOnClickListener { clearAll() }
        roundButton.setOnClickListener { roundResult() }
        percentButton.setOnClickListener { percentResult() }
        resultButton.setOnClickListener { equalAction() }
        pointButton.setOnClickListener { enterPoint() }


        return view
    }
    private fun numberEnter(view: View) // Обработчик нажатия - цифра (0-9)
    {
        // Константа, указывающая на последний элемент
        val lastElementOfNums = numbers.lastIndex

        if (view is Button && canEnterNumber) {
            if (resultText.text == "0")
                resultText.text = ""

            // 1. Запись в основное поле
            resultText.append(view.text)

            // 2. Добавление в массив введёного первого числа
            numbers[lastElementOfNums] =
                resultText.text.toString().replace(Regex("[+\\-*/]"), "").toDouble()

            // 3. Запись в поле истории
            historyText.append(view.text)
        }

        // Разрешает ввод операции
        canEnterOperation = true

        // Разрешает ввод точки (изначально выключен, воизбежании записи ".0123")
    }

    private fun operationEnter(view: View) // Обработчик нажатия - оператора (+ - * /)
    {
        if(canEnterOperation){
            numbers.add(0.0) // Добавление в массив введёного пустого числа
            operators.add("") //  Добавление в массив введёную операцию

            // Константа, указывающая на последний элемент
            val lastElementOfOperators = operators.lastIndex

            // Очистка основного поля
            clearResultField()

            if (view is Button && canEnterOperation) // Выбор операции
            {
                if (!canEnterNumber) {
                    historyText.text = ""
                    historyText.append(calculateResult.toString())
                }
                // 1. Запись в operator поле
                operatorText.text = view.text

                // 2. Добавление в массив введёного первого числа
                operators[lastElementOfOperators] = operatorText.text.toString() // 2. Добавление в массив введёной операции

                // 3. Запись в поле истории
                historyText.append(view.text)
            }
            // Блок ввода операции (воизбежании повторного ввода)
            canEnterOperation = false

            // Разрешает ввод чисел
            canEnterNumber = true
        }

    }

    fun enterPoint() // Ввод точки
    {
        // 1. После ввода - отключает возможность повторно ввести
        if (resultText.text.isNotEmpty()) {
            if (!resultText.text.contains(".")) {
                resultText.append(".")
                historyText.append(".")
            }
        }
    }

    private fun calculateResult(): Double {
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

    private fun equalAction() // Обработчик нажатия - равно(=)
    {
        // Расчёт результата
        val result = calculateResult()
        clearOperatorField()

        // Запись в основное поле
        resultText.text = result.toString()
        canEnterNumber = false
    }

    private fun clearAll() // Обработчик нажатия - очистка полей
    {
        numbers.clear()
        numbers.add(0.0)
        operators.clear()

        clearAllField()

        resultText.hint = "0"
        historyText.hint = "0"


        canEnterOperation = false
        canEnterNumber = true
    }

    private fun roundResult() {
        val result = resultText.text.toString().toDouble()
        resultText.text = (String.format("%.3f", result))
    }

    private fun percentResult() {
        val result = resultText.text.toString().toDouble()
        resultText.text = (result / 100.0).toString()
    }

    private fun clearOperatorField() { operatorText.text = "" }
    private fun clearResultField() { resultText.text = ""; resultText.hint = "0" }
    private fun clearHistoryField() { historyText.text = ""; historyText.hint = "0" }

    private fun clearAllField() {
        // Очищает Все поля
        clearResultField()
        clearHistoryField()
        clearOperatorField()
    }

}