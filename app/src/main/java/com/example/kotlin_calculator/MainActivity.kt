package com.example.kotlin_calculator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    // test
    private val constantSymbols : Array<String> = arrayOf("+", "-", "*", "/", "=") // constaints

    private val numbers = mutableListOf(0.0)
    private val operators = mutableListOf<String>()
    private var calculateResult : Double = 0.0

    private lateinit var resultText : TextView // Поле с результатом
    private lateinit var historyText : TextView // Поле истории (отображает ранее введённые числа/операторы)

    private  var canEnterPoint : Boolean = false
    private  var canEnterOperation : Boolean = false
    private  var canEnterNumber : Boolean = true

    private  lateinit var acButton : Button
    private  lateinit var pointButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultField)
        historyText = findViewById(R.id.historyField)

        acButton = findViewById(R.id.acBtn) // Ссылка на кнопку "All clean"
        pointButton = findViewById(R.id.pointBtn) // Ссылка на кнопку "All clean"
    }
    fun numberEnter(view: View) // Обработчик нажатия - цыфра (0-9)
    {
        // Константа, указывающая на последний эелемент
        val lastElementOfNums = numbers.lastIndex

        if(view is Button && canEnterNumber)
        {
            // 1. Запись в основное поле
            resultText.append(view.text)

            // 2. Добавление в массив введёного первого числа
            numbers[lastElementOfNums] = resultText.text.toString().replace(Regex("[+\\-*/]"), "").toDouble()

            // 3. Запись в поле истории
            historyText.append(view.text)
        }
        Log.i("InfoApp","first index: ${numbers.first()} | last index:${numbers.last()} | array ->"+ numbers.toString())

        // Разрешает ввод операции
        canEnterOperation = true

        // Разрешает ввод точки (изначально выключен, воизбежании записи ".0123")
        canEnterPoint = true

    }

    fun operationEnter(view: View) // Обработчик нажатия - оператора (+ - * /)
    {
        numbers.add(0.0) // 1. Добавление в массив введёного первого числа
        operators.add("") // 1. Добавление в массив введёного первого числа

        val lastElementOfOperators = operators.lastIndex // Константа, указывающая на последний эелемент

        resultText.text = ""

        if(view is Button && canEnterOperation) // Выбор операции
        {
            if(!canEnterNumber)
            {
                historyText.text = ""
                historyText.append(calculateResult.toString())
            }
            // 1. Запись в основное поле
            resultText.append(view.text)

            // 2. Добавление в массив введёного первого числа
            operators[lastElementOfOperators] = resultText.text.toString() // 2. Добавление в массив введёной операции

            // 3. Запись в поле истории
            historyText.append(view.text)
        }
        // Блок ввода операции (воизбежании повторного ввода)
        canEnterOperation = false

        // Разрешает ввод чисел
        canEnterNumber = true

        Log.i("InfoApp",operators.toString())

    }
    fun enterPoint(view: View) // Ввод точки
    {
        // 1. После ввода - отключает возможность повторно ввести
        canEnterPoint = false
    }
    private fun calculateResult() : Double{
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
        Log.d("InfoApp","Резултат:" + result.toString())

        numbers.clear()
        operators.clear()

        numbers.add(result)

        return result
    }
    fun equalAction(view: View) // Обработчик нажатия - равно(=)
    {
        setButtonColor(acButton,"#FF9900", Color.WHITE)
        val result = calculateResult()

        resultText.text = result.toString()

        calculateResult = result

        canEnterNumber = false
    }
    fun clearAll(view: View) // Обработчик нажатия - очистка полей
    {
        numbers.clear()
        operators.clear()
        numbers.add(0.0)

        resultText.text = ""
        historyText.text = ""

        canEnterPoint = false
        canEnterOperation = false
        canEnterNumber = true
        setButtonColor(acButton,"#969696", Color.BLACK)

        Log.d("InfoApp","Debug cleared: nums - ${numbers} | oper - ${operators}")
    }
    fun setButtonColor(objectButton : Button, bgColor : String, textColor : Int)
    {
        objectButton.setBackgroundColor(Color.parseColor(bgColor))
        objectButton.setTextColor(textColor)
    }

    fun switchToConverters(view: View){
        setContentView(R.layout.activity_converters)
    }
    fun switchToMain(view: View){
        setContentView(R.layout.activity_main)
    }
    fun switchToWeight(view: View){
        setContentView(R.layout.fragment_weight)
    }

    fun  quitApp(view : View) // Закрытие процесса
    {
        finish()
        exitProcess(0);
    }

}