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

    private fun calculateResult() : Array<Double>{
        return when(dataId){
            0 ->  MathFormulas.calculateParallelepipedProperties(listOf(dataFields[0],dataFields[1],dataFields[2]),true, "DebugTag")
            1 ->  MathFormulas.calculatePyramidProperties(listOf(dataFields[0],dataFields[1]),true, "DebugTag")
            2 ->  MathFormulas.calculateCylinderProperties(listOf(dataFields[0],dataFields[1]),true, "DebugTag")
            3 ->  MathFormulas.calculateConeProperties(listOf(dataFields[0],dataFields[1]),true, "DebugTag")
            4 ->  MathFormulas.calculateSphereProperties(listOf(dataFields[0]),true, "DebugTag")
            else -> emptyArray()
        }
    }

    // Метод для добавления готовой разметки в контейнер и возврата ссылок на TextView с идентификатором resultTextMath
    private fun addViewsToContainer(container: LinearLayout, count: Int): Array<TextView?>? {
        val inflater = LayoutInflater.from(context)
        val addedTextViews = arrayOfNulls<TextView>(count)
        for (i in 0 until count) {
            // Замените на свой идентификатор готовой разметки
            val resultMathLayout: View = inflater.inflate(R.layout.result_math, container, false)

            // Находим TextView внутри добавленной разметки по идентификатору
            val resultTextMath = resultMathLayout.findViewById<TextView>(R.id.resultTextMath)

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
    private fun hintSetToText(textViewFields: Array<TextView?>, hints: Array<Array<Int>>){
        for((index, field) in textViewFields.withIndex()){
            field?.hint = resources.getText(hints[0][index])
        }
    }

    private fun dataSetIdGetting(dataSetIndex: Int) {
        // Назначает нужный датасет и подготавливает подсказки калькулятора под выбранный датасет
        val dataSet = constantsAlgebra[dataSetIndex]
        labelCalculator.text = resources.getText(dataSet[0])
        tooltipCalculator.text = resources.getText(dataSet[1])
        when(dataId){
            0 -> { // Параллелепипед (куб)
                dataFields = parallelepipedInputElements()
                textFields = addViewsToContainer(resultContainerLayout, 3)!!
                hintSetToText(textFields, arrayOf(
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))
                )
            }
            1 -> { // Пирамида
                dataFields = pyramidInputElements()
                textFields = addViewsToContainer(resultContainerLayout, 3)!!
                hintSetToText(textFields, arrayOf(
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))
                )
            }
            2 -> { // Цилиндр
                dataFields = pyramidInputElements()
                textFields = addViewsToContainer(resultContainerLayout, 3)!!
                hintSetToText(textFields, arrayOf(
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))
                )
            }
            3 -> { // Конус
                dataFields = pyramidInputElements()
                textFields = addViewsToContainer(resultContainerLayout, 3)!!
                hintSetToText(textFields, arrayOf(
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea, R.string.label_resultVolume))
                )
            }
            4 -> { // Сфера
                dataFields = pyramidInputElements()
                textFields = addViewsToContainer(resultContainerLayout, 2)!!
                hintSetToText(textFields, arrayOf(
                    arrayOf(R.string.label_resultTotalArea, R.string.label_resultLateralArea))
                )
            }
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
    private fun pyramidInputElements(): Array<EditText> {
        // Данный метод отвечает за расстановку и настройку полей ввода данных необходимых для параллелепипеда
        val sideFields = arrayOf(EditText(context), EditText(context)) // Массив самих полей

        sideFields[0].hint = resources.getText(R.string.hint_sideA)
        sideFields[1].hint = resources.getText(R.string.hint_height)

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

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }
    fun imageSet(newDrawable : Drawable){ // Назначает ресурс drawable для переменной
        this.drawableResource = newDrawable
    }
}