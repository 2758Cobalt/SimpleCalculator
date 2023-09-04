package com.example.kotlin_calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    private val constantSymbols : Array<String> = arrayOf("+", "-", "*", "÷", "=") // constaints

    private var numberFirst : Double = 0.0 // Первое число
    private var numberSecond : Double = 0.0 // Второе число


    private var canEnterSecondNumber : Boolean = false
    private var canAddOperation : Boolean = false
    private var resultStatus : Boolean = false

    private var operator : String = ""
    private var valueField : String = ""

    private lateinit var resultField : TextView // Поле с с результатом
    private lateinit var historyField : TextView // Поле истории (отображает ранее введённые числа/операторы)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultField = findViewById(R.id.resultField)
        historyField = findViewById(R.id.historyField)
    }
    fun numberEnter(view: View){
        // Обработчик нажатия - цыфра (0-9)
        if(resultStatus)
            restoreVariables(true)

        if(view is Button)
        {
            resultField.append(view.text) // К полю добавляется введенное число

            if(operator == "") // если знака операции - нет
            {
                valueField = resultField.text.toString() // конвертирует в строку поле и хранит его

                numberFirst = valueField.toDouble() // Конвертация текстового поля в тип double
            }
            if(canEnterSecondNumber) // Если разрешён ввод второго числа
            {
                valueField = resultField.text.toString() // конвертирует в строку поле и хранит его

                numberSecond = valueField.toDouble() // Конвертация текстового поля в тип double

            }

            historyField.append(view.text) // Добавление чисел в поле истории

        }

        canAddOperation = true // Разрешает назначения оператора

    }

    fun operationEnter(view: View){
        // Обработчик нажатия - оператора (+ - x /)

        resultField.text = ""

        calcualteResult()


        if(view is Button && canAddOperation)
        {
            operator = view.text.toString() // Добавление символа(оператора) в строку

            historyField.append(operator)

            canEnterSecondNumber = true
        }

        canAddOperation = false // Блок ввода операции

    }
    fun restoreVariables(clearText : Boolean = true){
        if(clearText)
        {
            resultField.text = ""
            historyField.text = ""
        }

        numberFirst = 0.0
        numberSecond = 0.0

        operator = ""

        resultStatus = false
        canAddOperation = false
        canEnterSecondNumber = false

        Log.i("InfoApp","AllClear")
    }
    fun calcualteResult() : Double
    {
        when(operator){
            constantSymbols[0] -> numberFirst += numberSecond
            constantSymbols[1] -> numberFirst -= numberSecond
            constantSymbols[2] -> numberFirst *= numberSecond
            constantSymbols[3] -> numberFirst /= numberSecond
        }
        numberSecond
        return numberFirst
    }
    fun clearAll(view: View) {
        // Обработчик нажатия - очистка полей
        restoreVariables()

    }
    fun equalAction(view: View){
        // Обработчик нажатия - равно(=)

        resultField.text = calcualteResult().toString()
        restoreVariables(false)
        resultStatus = true
    }

    fun  quitApp(view : View) {
        // Закрытие процесса

        finish()
        exitProcess(0);
    }

}