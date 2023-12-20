package com.example.kotlin_calculator

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment


class MathFigureFragment : Fragment() {
    private val constantsAlgebra = Constants.constantsMathData
    private var dataFields : Array<EditText> = arrayOf()

    private lateinit var labelCalculator : TextView
    private lateinit var tooltipCalculator : TextView

    private lateinit var buttonReset : ImageButton

    private lateinit var mathCalculatorPreview : ImageView
    private lateinit var textFields : Array<TextView?>

    private lateinit var drawableResource : Drawable

    private lateinit var inputContainerLayout: LinearLayout
    private lateinit var resultContainerLayout: LinearLayout

    private var dataId = 0 // Набор данных

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_math, container, false) // Сам view фрагмента
        var result : Array<Double>

        labelCalculator = view.findViewById(R.id.labelMathCalculator)// Заголовок окна
        tooltipCalculator = view.findViewById(R.id.tooltipMathCalculator) // Описание окна

        inputContainerLayout = view.findViewById(R.id.inputContainer) // Контейнер для ввода
        resultContainerLayout = view.findViewById(R.id.resultContainer) // Контейнер для ввода

        buttonReset = view.findViewById(R.id.buttonResetMath) // Контейнер для ввода
        val btnBack = view.findViewById(R.id.backToMenu) as ImageButton

        mathCalculatorPreview = view.findViewById(R.id.calculatorPreview)
        mathCalculatorPreview.setImageDrawable(drawableResource)

        btnBack.setOnClickListener {parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, SelectorFragment()).commit()  }
        buttonReset.setOnClickListener { result = calculateResult(); showResult(result, textFields) }

        dataSetIdGetting(dataId)

        return view
    }
    private fun resetFieldsAction(){
        for (field in dataFields){ // field : EditText
            field.setText("")
        }
    }



    // Метод для добавления готовой разметки в контейнер и возврата ссылок на TextView с идентификатором resultTextMath
    private fun addViewsToContainer(container: LinearLayout, hints: Array<Int>): Array<TextView?>? {
        val inflater = LayoutInflater.from(context)
        val addedTextViews = arrayOfNulls<TextView>(hints.size)

        for (i in hints.indices) {
            // Замените на свой идентификатор готовой разметки
            val resultMathLayout: View = inflater.inflate(R.layout.result_math, container, false)

            // Находим TextView внутри добавленной разметки по идентификатору
            val resultTextMath = resultMathLayout.findViewById<TextView>(R.id.resultTextMath)

            // Назначение подсказки
            resultTextMath.hint = resources.getText(hints[i])

            // Добавляем разметку в контейнер
            container.addView(resultMathLayout)

            // Добавляем ссылку на TextView с идентификатором resultTextMath в массив
            addedTextViews[i] = resultTextMath
        }
        return addedTextViews
    }

    private fun showResult(result: Array<Double>, fields: Array<TextView?>?){
        for ((index,answer) in fields!!.withIndex()) {
            answer?.text = result[index].toString()
        }
    }
    private fun calculateResult() : Array<Double>{
        return when(dataId){
            0 ->  GeometricSolidsFormulas.calculateParallelepipedProperties(listOf(dataFields[0],dataFields[1],dataFields[2]),false, "DebugTag")
            1 ->  GeometricSolidsFormulas.calculatePyramidProperties(listOf(dataFields[0],dataFields[1]),false, "DebugTag")
            2 ->  GeometricSolidsFormulas.calculateCylinderProperties(listOf(dataFields[0],dataFields[1]),false, "DebugTag")
            3 ->  GeometricSolidsFormulas.calculateConeProperties(listOf(dataFields[0],dataFields[1]),false, "DebugTag")
            4 ->  GeometricSolidsFormulas.calculateSphereProperties(listOf(dataFields[0]),false, "DebugTag")
            5 ->  GeometricSolidsFormulas.calculatePrismProperties(listOf(dataFields[0],dataFields[1],dataFields[2]),false, "DebugTag")

            6 ->  GeometricFiguresFormulas.calculateRectangle(listOf(dataFields[0],dataFields[1]),false, "DebugTag")
            7 ->  GeometricFiguresFormulas.calculateTriangleProperties(listOf(dataFields[0],dataFields[1],dataFields[2]),false, "DebugTag")
            8 ->  GeometricFiguresFormulas.calculateRhombusProperties(listOf(dataFields[0],dataFields[1]),false, "DebugTag")
            else -> emptyArray()
        }
    }
    private fun dataSetIdGetting(dataSetIndex: Int) {
        // Назначает нужный датасет и подготавливает подсказки калькулятора под выбранный датасет
        val dataSet = constantsAlgebra[dataSetIndex]
        labelCalculator.text = resources.getText(dataSet[0])
        tooltipCalculator.text = resources.getText(dataSet[1])
        when(dataId){
            0 -> {
                // Параллелепипед (куб) - принимает (сторона A, сторона B, сторона C)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_sideC), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))!!
            }
            1 -> {
                // Пирамида - принимает (сторона A, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_height), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))!!
            }
            2 -> {
                // Цилиндр - принимает (радиус r, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_radius,
                    R.string.hint_height), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))!!
            }
            3 -> {
                // Конус - принимает (радиус r, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_radius,
                    R.string.hint_height), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))!!
            }
            4 -> {
                // Сфера - принимает (радиус r)
                dataFields = inputElementsGenerator(arrayOf(R.string.hint_radius),  inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultVolume))!!
            }
            5 -> {
                // Призма - принимает (---)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_length,
                    R.string.hint_height,
                    R.string.hint_sideC,), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultVolume))!!
            }
            6 -> {
                // Прямоугольник - принимает (сторона A, сторона B)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultPerimeter, R.string.hint_diagonalFirst,R.string.label_resultArea))!!
            }
            7 -> {
                // Треугольник - принимает (сторона A, сторона B, сторона C)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_sideC), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultPerimeter, R.string.hint_diagonalFirst,R.string.label_resultArea))!!
            }
            8 -> {
                // Ромб - принимает (сторона ромба A,  высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultPerimeter, R.string.hint_diagonalFirst,R.string.label_resultArea))!!
            }
        }
    }

    private fun inputElementsGenerator(inputFieldsHintId: Array<Int>, container: LinearLayout): Array<EditText> {
        var editTextArray: Array<EditText> = arrayOf()
        // Данный метод отвечает за расстановку и настройку полей ввода данных необходимых для параллелепипеда

        for (element in inputFieldsHintId) {
            var inputTextElement = EditText(context)
            inputTextElement.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing))
            }
            inputTextElement.textSize = 16F
            inputTextElement.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            inputTextElement.hint = resources.getText(element)
            inputTextElement.setEms(10)
            inputTextElement.inputType = InputType.TYPE_CLASS_NUMBER

            container.addView((inputTextElement))
            editTextArray += inputTextElement
        }
        return editTextArray
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }
    fun imageSet(newDrawable : Drawable){ // Назначает ресурс drawable для переменной
        this.drawableResource = newDrawable
    }
}
