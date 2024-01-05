package com.example.kotlin_calculator.fragments

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.activities.MainActivity
import com.example.kotlin_calculator.references.ConstantsConverter
import com.example.kotlin_calculator.references.GeometricFiguresFormulas
import com.example.kotlin_calculator.references.GeometricSolidsFormulas


class MathFigureFragment : Fragment() {
    private val constantsAlgebra = ConstantsConverter.constantsMathData
    private var dataFields : Array<EditText> = arrayOf()

    private lateinit var labelCalculator : TextView
    private lateinit var descCalculator : TextView

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
        descCalculator = view.findViewById(R.id.descMathCalculator) // Описание окна

        inputContainerLayout = view.findViewById(R.id.inputContainer) // Контейнер для ввода
        resultContainerLayout = view.findViewById(R.id.resultContainer) // Контейнер с результатами

        buttonReset = view.findViewById(R.id.buttonResetMath)
        val btnBack = view.findViewById(R.id.backToMenu) as ImageView

        mathCalculatorPreview = view.findViewById(R.id.calculatorPreview)
        mathCalculatorPreview.setImageDrawable(drawableResource)

        btnBack.setOnClickListener { (requireActivity() as MainActivity).replaceFragmentInViewPager(1, SelectorFragment()) }
        buttonReset.setOnClickListener { result = calculateResult(); showResult(result, textFields) }

        dataSetIdGetting(dataId)

        return view
    }
    private fun resetFieldsAction(){
        for (field in dataFields){ // field : EditText
            field.setText("")
        }
    }

    private fun showResult(result: Array<Double>, fields: Array<TextView?>?){
        if (result.isNotEmpty() && fields!!.isNotEmpty()){
            for ((index,answer) in fields.withIndex()) {
                answer?.text = result[index].toString()
            }
        }
    }
    private fun calculateResult() : Array<Double>{
        try {
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
                9 ->  GeometricFiguresFormulas.calculateCircleProperties(listOf(dataFields[0]),false, "DebugTag")
                10 ->  GeometricFiguresFormulas.calculateTrapezoidProperties(listOf(dataFields[0],dataFields[1],dataFields[2],dataFields[3],dataFields[4]),false, "DebugTag")
                else -> emptyArray()
            }
        }
        catch (ex: NumberFormatException){
            Toast.makeText(context,"Перехвачено исключение: ${ex}", Toast.LENGTH_SHORT).show()
            return emptyArray()
        }

    }
    private fun dataSetIdGetting(dataSetIndex: Int) {
        // Назначает нужный датасет и подготавливает подсказки калькулятора под выбранный датасет
        val dataSet = constantsAlgebra[dataSetIndex]
        labelCalculator.text = resources.getText(dataSet[0])
        descCalculator.text = resources.getText(dataSet[1])
        when(dataId){
            0 -> {
                // Параллелепипед (куб) - принимает (сторона A, сторона B, сторона C)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_sideC
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultTotalAreaHint,
                        R.string.label_resultLateralAreaHint,
                        R.string.label_resultVolumeHint
                    ),
                    arrayOf(
                        R.string.symbol_totalArea,
                        R.string.symbol_lateralArea,
                        R.string.symbol_volume
                    ))!!
            }
            1 -> {
                // Пирамида - принимает (сторона A, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_height
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultTotalAreaHint,
                        R.string.label_resultLateralAreaHint,
                        R.string.label_resultVolumeHint
                    ),
                    arrayOf(
                        R.string.symbol_totalArea,
                        R.string.symbol_lateralArea,
                        R.string.symbol_volume
                    ))!!
            }
            2 -> {
                // Цилиндр - принимает (радиус r, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_radius,
                    R.string.hint_height
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultTotalAreaHint,
                        R.string.label_resultLateralAreaHint,
                        R.string.label_resultVolumeHint
                    ),
                    arrayOf(
                        R.string.symbol_totalArea,
                        R.string.symbol_lateralArea,
                        R.string.symbol_volume
                    ))!!
            }
            3 -> {
                // Конус - принимает (радиус r, высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_radius,
                    R.string.hint_height
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultTotalAreaHint,
                        R.string.label_resultLateralAreaHint,
                        R.string.label_resultVolumeHint
                    ),
                    arrayOf(
                        R.string.symbol_totalArea,
                        R.string.symbol_lateralArea,
                        R.string.symbol_volume
                    ))!!
            }
            4 -> {
                // Сфера - принимает (радиус r)
                dataFields = inputElementsGenerator(arrayOf(R.string.hint_radius),  inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalAreaHint, R.string.label_resultVolumeHint),
                    arrayOf(R.string.symbol_totalArea, R.string.symbol_volume))!!
            }
            5 -> {
                // Призма - принимает (---)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_length,
                    R.string.hint_height,
                    R.string.hint_sideC,), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultTotalAreaHint, R.string.label_resultVolumeHint),
                    arrayOf(R.string.symbol_totalArea, R.string.symbol_volume))!!
            }
            6 -> {
                // Прямоугольник - принимает (сторона A, сторона B)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultPerimeter,
                        R.string.hint_diagonal,
                        R.string.label_resultArea
                    ),
                    arrayOf(
                        R.string.symbol_perimeter,
                        R.string.symbol_diagonal,
                        R.string.symbol_area
                    ))!!
            }
            7 -> {
                // Треугольник - принимает (сторона A, сторона B, сторона C)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_sideC
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultPerimeter,
                        R.string.hint_diagonal,
                        R.string.label_resultArea
                    ),
                    arrayOf(
                        R.string.symbol_perimeter,
                        R.string.symbol_diagonal,
                        R.string.symbol_area
                    ))!!
            }
            8 -> {
                // Ромб - принимает (сторона ромба A,  высота h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_length,
                    R.string.hint_height
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultPerimeter,
                        R.string.hint_diagonal,
                        R.string.label_resultArea
                    ),
                    arrayOf(
                        R.string.symbol_perimeter,
                        R.string.symbol_diagonal,
                        R.string.symbol_area
                    ))!!
            }
            9 -> {
                // Круг - принимает (радиус r)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_radius
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(R.string.label_resultPerimeter, R.string.label_circleLength),
                    arrayOf(R.string.symbol_perimeter, R.string.symbol_length))!!
            }
            10 -> {
                // Трапеция - принимает (основу A и B, боковую сторону A и B, высоту h)
                dataFields = inputElementsGenerator(arrayOf(
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_sideA,
                    R.string.hint_sideB,
                    R.string.hint_height
                ), inputContainerLayout)
                textFields = addViewsToContainer(resultContainerLayout,
                    arrayOf(
                        R.string.label_resultArea,
                        R.string.label_resultPerimeter,
                        R.string.hint_height
                    ),
                    arrayOf(R.string.symbol_area, R.string.symbol_perimeter, R.string.symbol_height))!!
            }
        }
    }

    private fun inputElementsGenerator(inputFieldsHintId: Array<Int>, container: LinearLayout): Array<EditText> {
        var editTextArray: Array<EditText> = arrayOf()
        // Данный метод отвечает за расстановку и настройку полей ввода данных необходимых для параллелепипеда

        for (element in inputFieldsHintId) {
            var inputTextElement = EditText(context)
            inputTextElement.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing),
                    resources.getDimensionPixelSize(R.dimen.nav_header_vertical_spacing))
            }

            //  Назначение подсказки
            inputTextElement.hint = resources.getText(element)

            // Назначение прочих параметров
            inputTextElement.textSize = 16F
            inputTextElement.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            inputTextElement.setTextColor(resources.getColor(R.color.gray120))
            inputTextElement.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white200))
            inputTextElement.setHintTextColor(resources.getColor(R.color.gray120))
            inputTextElement.setLinkTextColor(resources.getColor(R.color.orange))
            inputTextElement.setEms(10)
            inputTextElement.inputType = InputType.TYPE_CLASS_NUMBER

            container.addView((inputTextElement))
            editTextArray += inputTextElement
        }
        return editTextArray
    }


    private fun addViewsToContainer(container: LinearLayout, textViewsHints: Array<Int>, buttonsHints: Array<Int>): Array<TextView?>? {
        // Метод для добавления готовой разметки в контейнер и возврата ссылок на TextView с идентификатором resultTextMath
        val inflater = LayoutInflater.from(context)
        val addedTextViews = arrayOfNulls<TextView>(textViewsHints.size)

        for (i in textViewsHints.indices) {
            // Замените на свой идентификатор готовой разметки
            val resultMathLayout: View = inflater.inflate(R.layout.math_resultfield, container, false)

            // Находим TextView внутри добавленной разметки по идентификатору
            val resultTextMath = resultMathLayout.findViewById<TextView>(R.id.resultTextMath)
            val btnTooltip = resultMathLayout.findViewById<Button>(R.id.result_tooltipBtn)

            // Назначение подсказки
            btnTooltip.text =  resources.getText(buttonsHints[i])
            resultTextMath.hint =  resources.getText(textViewsHints[i])

            // Добавляем разметку в контейнер
            container.addView(resultMathLayout)

            // Добавляем ссылку на TextView с идентификатором resultTextMath в массив
            addedTextViews[i] = resultTextMath
        }
        return addedTextViews
    }

    fun dataIdSet(newDataSetId : Int){
        this.dataId = newDataSetId
    }
    fun imageSet(newDrawable : Drawable){ // Назначает ресурс drawable для переменной
        this.drawableResource = newDrawable
    }
}
