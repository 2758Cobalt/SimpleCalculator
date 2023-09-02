package com.example.kotlin_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
        if(view is Button)
        {
            if(resultStatus)
            {
                calcField?.text = ""
                resultStatus = false
            }

            if(view.text == ",")
            {
                if(canAddDecimal)
                {
                    calcField?.append(view.text)
                }
                canAddDecimal = false
            }
            else
            {
                calcField?.append(view.text)
            }
            canAddOperation = true
        }
    }
    fun operationAction(view: View){
        if(view is Button && canAddOperation)
        {
            if(resultStatus)
                resultStatus = false

            calcField?.append(view.text)
            canAddOperation = false
        }
    }

    fun clearAll(view: View){
        calcField?.text = ""
    }
    fun equalAction(view: View){
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

    private  fun digitsOperators(): MutableList<Any>
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
        return  list
    }


}