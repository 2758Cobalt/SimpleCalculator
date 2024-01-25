package com.example.kotlin_calculator.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.references.ConstantsCalculator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class ConverterFragment: BottomSheetDialogFragment() {
    private var calculatorFragment: CalculatorFragment? = null
    private var descResources = ConstantsCalculator.stringResourcesTrigonometryDesc

    private lateinit var pickerRange: NumberPicker
    private lateinit var previewResultText: TextView
    private lateinit var inputText: EditText
    private lateinit var enterButton: Button

    private var roundRange: Int = 10 // Default
    private var resultCalculation: Double = 0.0
    var dataId: Int = 0

    fun setParentFragment(parent: CalculatorFragment){
        calculatorFragment = parent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_convert_display, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputText = view.findViewById(R.id.inputEditText)
        previewResultText = view.findViewById(R.id.preResultCalculation)
        pickerRange = view.findViewById(R.id.roundRangePicker)

        enterButton = view.findViewById(R.id.enterAction)

        configureNumberPicker(pickerRange)

        enterButton.setOnClickListener {
            calculatorFragment!!.numberEnterAction(calculate()) // Если calculatorFragment non-null, то вызывает у него функцию ввода
            dismiss() // Закрывает диалог
        }

        pickerRange.setOnValueChangedListener { _, _, newVal ->
            roundRange = newVal
            previewResultCalculation(previewResultText)
        }

        val descText = view.findViewById(R.id.descAdditional) as TextView
        descText.text = resources.getString(descResources[dataId])


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // Вызывается до изменения текста
            override fun afterTextChanged(p0: Editable?) {} // Вызывается после изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { previewResultCalculation(previewResultText) } // Вызывается во время изменения текста
        }

        inputText.addTextChangedListener(textWatcher)
    }


    private fun previewResultCalculation(textView: TextView){ // Предосмотр результата перед отправкой
        calculate()
        if (inputText.text.toString() != "0"){
            textView.text = String.format(Locale.US, "%.${roundRange}f", resultCalculation)
        }
    }

    private fun calculate() : String{
        if (inputText.text.isNotEmpty()){
            val input = inputText.text.toString().toDouble()

            when(dataId){
                0 ->{
                    resultCalculation = sin(input) // Sin() - Синус
                }
                1 ->{
                    resultCalculation = cos(input) // Cos() - Косинус
                }
                2 ->{
                    resultCalculation = tan(input) // Tan() - Тангенс
                }
                3 ->{
                    resultCalculation = 1.0 / tan(input) // Cotan - Котангенс
                }
            }
        }
        return String.format(Locale.US, "%.${roundRange}f", resultCalculation)
    }

    private fun configureNumberPicker(numberPicker: NumberPicker) {
        numberPicker.minValue = 2
        numberPicker.maxValue = 15
        numberPicker.value = roundRange // default value
        numberPicker.wrapSelectorWheel = false
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    }
}