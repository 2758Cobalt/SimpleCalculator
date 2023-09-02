package com.example.kotlin_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var calcField : TextView? = null
    private var canAddOperation : Boolean = false
    private var canAddDecimal : Boolean = true
    private var resultStatus : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calcField = findViewById(R.id.resultField)

    }
    fun numAction(view: View){
        // Обработчик нажатия - цыфра (0-9)

        if(view is Button) {
            if(resultStatus) {
                calcField?.text = ""
                resultStatus = false
            }

            if(view.text == ",") {
                if(canAddDecimal)
                {
                    calcField?.append(view.text)
                }
                canAddDecimal = false
            }
            else {
                calcField?.append(view.text)
            }
            canAddOperation = true
        }
    }
    fun operationAction(view: View){
        // Обработчик нажатия - оператора (+ - x /)

        if(view is Button && canAddOperation) {
            if(resultStatus)
                resultStatus = false

            calcField?.append(view.text)
            canAddOperation = false
        }
    }

    fun clearAll(view: View) {
        // Обработчик нажатия - очистка поля

        calcField?.text = ""
        resultStatus = false
        canAddOperation = false
        canAddDecimal = true
    }
    fun equalAction(view: View){
        // Обработчик нажатия - равно(=)

        calcField?.text = calculateResults()
        resultStatus = true
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timeDivision = timesDivisionCalculate(digitsOperators)
        if(timeDivision.isEmpty()) return ""
        val result = addSubtractCalculate(timeDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for (i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float

                if(operator == '+')
                {
                    result += nextDigit
                }
                if(operator == '-')
                {
                    result -= nextDigit
                }
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('÷'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    '÷' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restartIndex)
            {
                newList.add(passedList[i])
            }
        }

        return newList

    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        val textResult : String = calcField?.text.toString()
        for (character in textResult)
        {
            if(character.isDigit())
            {
                currentDigit += character
            }
            else{
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                    list.add(character)
            }
        }
        if(currentDigit != "")
        {

            list.add(currentDigit.toFloat())

        }
        return list
    }

    fun  quitApp(view : View) {
        // Закрытие процесса

        finish()
        exitProcess(0);
    }
    fun convertAction(view : View){
        val result = calcField?.text
        var toast = Toast.makeText(this, result, Toast.LENGTH_SHORT)
        toast.show();
    }
    fun debugMsg(view: View){
        var toast = Toast.makeText(this, "test", Toast.LENGTH_SHORT)
            toast.show();
    }

}