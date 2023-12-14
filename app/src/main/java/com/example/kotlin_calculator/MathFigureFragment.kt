package com.example.kotlin_calculator

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class MathFigureFragment : Fragment() {
    private val constantsAlgebra = Constants.constantsAlgebraData
    private var dataFields : Array<EditText> = arrayOf()

    private lateinit var labelCalculator : TextView
    private lateinit var tooltipCalculator : TextView
    private lateinit var resultCalculation : TextView

    private lateinit var buttonReset : ImageButton

    private lateinit var inputContainerLayout: LinearLayout
    private var dataId = 0 // Набор данных

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_math, container, false) // Сам view фрагмента

        labelCalculator = view.findViewById(R.id.labelMathCalculator)// Заголовок окна
        tooltipCalculator = view.findViewById(R.id.tooltipMathCalculator) // Описание окна
        resultCalculation = view.findViewById(R.id.mathResult) // Поле с результатом вычисления

        inputContainerLayout = view.findViewById(R.id.inputContainer) // Контейнер для ввода
        buttonReset = view.findViewById(R.id.buttonMathReset) // Контейнер для ввода

        dataSetIdGetting(dataId)

        buttonReset.setOnClickListener { calculateResult() }

        return view
    }
    private fun resetFieldsAction(){
        resultCalculation.text = ""
    }
    private fun calculateResult(){
        when(dataId){
            0 -> {
                MathFormulas.calculateParallelepipedProperties(
                    listOf(dataFields[0],dataFields[1],dataFields[2]),true, "DebugTag")
            }
            1 ->
                MathFormulas.calculateCubeProperties(
                    dataFields[0], true, "DebugTag")
        }

    }
    private fun dataSetIdGetting(dataSetIndex: Int) {
        // Назначает нужный датасет и подготавливает подсказки калькулятора под выбранный датасет
        val dataSet = constantsAlgebra[dataSetIndex]
        labelCalculator.text = resources.getText(dataSet[0])
        tooltipCalculator.text = resources.getText(dataSet[1])
        resultCalculation.hint = resources.getText(dataSet[0])
        when(dataId){
            0 -> {
                dataFields = parallelepipedInputElements()
            }
            1 -> dataFields = cubeInputElements()
        }
    }
    private fun parallelepipedInputElements(): Array<EditText> {
        // Данный метод отвечает за расстановку и настройку полей ввода данных необходимых для параллелепипеда
        val sideFields = arrayOf(EditText(context), EditText(context), EditText(context)) // Массив самих полей

        sideFields[0].hint = resources.getText(R.string.hint_sideA)
        sideFields[1].hint = resources.getText(R.string.hint_sideB)
        sideFields[2].hint = resources.getText(R.string.hint_sideC)

        for (field in sideFields) {
            field.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing))
            }

            field.textSize = 16F
            field.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            field.setEms(10)
            field.inputType = InputType.TYPE_CLASS_NUMBER

            inputContainerLayout.addView(field) // Добавляет элемент в контейнер
        }
        return sideFields
    }
    private fun cubeInputElements(): Array<EditText> {
        val sideCube = EditText(context)

        sideCube.hint = resources.getText(R.string.tooltip_enterText)

        sideCube.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            setMargins(
                resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing)) }
        sideCube.textSize = 16F
        sideCube.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        sideCube.setEms(10)
        sideCube.inputType = InputType.TYPE_CLASS_NUMBER
        inputContainerLayout.addView(sideCube) // Добавляет элемент в контейнер

        return arrayOf(sideCube)
    }

    fun dataIdGet() : Int{
        return this.dataId
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }
}