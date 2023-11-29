package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class WeightFragment: Fragment() {
    private lateinit var constants: Array<String>

    private lateinit var valueForConvert: EditText

    private lateinit var resultConvert: TextView

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    private lateinit var dropdownFrom: Spinner
    private lateinit var dropdownIn: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_weight, container, false) // Сам view фрагмента

        constants = resources.getStringArray(R.array.weight_values)

        dropdownFrom = view.findViewById(R.id.dropDownFrom)
        dropdownIn = view.findViewById(R.id.dropDownIn)

        valueForConvert = view.findViewById(R.id.inputValue)
        resultConvert = view.findViewById(R.id.resultConvertation)

        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton.setOnClickListener { calculateConvertation(it) }
        resetButton.setOnClickListener { resetFields(it) }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weight_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }

        return view
    }
    fun calculateConvertation(view : View) {

        // Подсчёт массы
        if(valueForConvert.text.isNotEmpty()) // Проверяет не пустое ли поле ввода
        {
            val value = valueForConvert.text.toString().toDouble()
            var result = 0.0
            when (dropdownFrom.selectedItem.toString()) {

                // Микрограмм
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 1) // 1
                    constants[1] -> result = (value / 1000) // 1 : 1000 - ug -> miligram
                    constants[2] -> result = (value / 1000000) // 1 : 1e-6 (0,000 001) -> gram
                    constants[3] -> result =
                        (value / 1000000000) // 1 : 1e-9 (0, 000 000 001) -> kilogram
                    constants[4] -> result =
                        (value / 1000000000000) // 1 : 1e-12 (0, 000 000 000 001) - tonna
                    constants[5] -> result =
                        (value / 1000000000000000) // 1 : 1e-15 (0, 000 000 000 001) - megatonna
                    constants[6] -> result =
                        (value / 1000000000000000000) // 1 : 1e-18 (0, 000 000 000 001) - kilotonna
                }

                // Миллиграмм
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.001) // 1 : 0.001
                    constants[1] -> result = (value / 1) // 1 : 1
                    constants[2] -> result = (value / 1000) // 1 : 1e-3
                    constants[3] -> result = (value / 1000000) // 1 : 1e-6
                    constants[4] -> result = (value / 1000000000) // 1 : 1e-9
                    constants[5] -> result = (value / 1000000000000) // 1 : 1e-12
                    constants[6] -> result = (value / 1000000000000000) // 1 : 1e-15
                }

                // Грамм
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.000001) // 1 : 1e+6
                    constants[1] -> result = (value / 0.001) // 1 : 1e+3
                    constants[2] -> result = (value / 1) // 1 : 1
                    constants[3] -> result = (value / 1000) // 1 : 1e-3
                    constants[4] -> result = (value / 1000000) // 1 : 1e-6
                    constants[5] -> result = (value / 1000000000) // 1 : 1e-9
                    constants[6] -> result = (value / 1000000000000) // 1 : 1e-12
                }

                // Килограмм
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.000000001) // 1 : 1e+9
                    constants[1] -> result = (value / 0.000001) // 1 : 1e+6
                    constants[2] -> result = (value / 0.001) // 1 : 1e+3
                    constants[3] -> result = (value / 1) // 1 : 1
                    constants[4] -> result = (value / 1000) // 1 : 1e-3
                    constants[5] -> result = (value / 1000000) // 1 : 1e-6
                    constants[6] -> result = (value / 1000000000) // 1 : 1e-9
                }
                // Тонна
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.000000000001) // 1 : 1e+12
                    constants[1] -> result = (value / 0.000000001) // 1 : 1e+9
                    constants[2] -> result = (value / 0.000001) // 1 : 1e+6
                    constants[3] -> result = (value / 0.001) // 1 : 1e+3
                    constants[4] -> result = (value / 1) // 1 : 1
                    constants[5] -> result = (value / 1000) // 1 : 1e-3
                    constants[6] -> result = (value / 1000000) // 1 : 1e-6
                }

                // Мегатонна
                constants[5] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.000000000000001) // 1 : 1e+15
                    constants[1] -> result = (value / 0.000000000001) // 1 : 1e+12
                    constants[2] -> result = (value / 0.000000001) // 1 : 1e+9
                    constants[3] -> result = (value / 0.000001) // 1 : 1e+6
                    constants[4] -> result = (value / 0.001) // 1 : 1e+3
                    constants[5] -> result = (value / 1) // 1 : 1
                    constants[6] -> result = (value / 1000) // 1 : 1e-3
                }

                // Килотона
                constants[6] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value / 0.000000000000000001) // 1 : 1e+18
                    constants[1] -> result = (value / 0.000000000000001) // 1 : 1e+15
                    constants[2] -> result = (value / 0.000000000001) // 1 : 1e+12
                    constants[3] -> result = (value / 0.000000001) // 1 : 1e+9
                    constants[4] -> result = (value / 0.000001) // 1 : 1e+6
                    constants[5] -> result = (value / 0.001) // 1 : 1e+3
                    constants[6] -> result = (value / 1) // 1 : 11
                }
            }
            resultConvert.text = "≈" + result.toString()
        }
    }

    fun resetFields(view: View) {
        resultConvert.text = "0"
        valueForConvert.setText("0")
    }
}