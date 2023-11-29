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

class TemperatureFragment: Fragment() {
    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_temperature, container, false) // Сам view фрагмента

        constants = resources.getStringArray(R.array.temperature_values)

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
            R.array.temperature_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Установка строкового массива
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Применение адаптера к спинеру
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }

        return view
    }
    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        resultConvert.text ="0"
        valueForConvert.setText("0")
    }
    fun calculateConvertation(view : View){
        // Расчёт данных
        if(valueForConvert.text.isNotEmpty()) // Проверяет не пустое ли поле ввода
        {
            val value = valueForConvert.text.toString().toDouble()
            var result = 0.0

            when(dropdownFrom.selectedItem.toString()) {

                // Цельсий
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = value // C
                    constants[1] -> result = ((value * 9 / 5) + 32) // F
                    constants[2] -> result = (value + 273.15) // K
                    constants[3] -> result = (value + 273.15) * 1.8 // R
                    constants[4] -> result = (value * 4 / 5) // Re

                }

                // Фаренгейт
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = ((value - 32) * 5 / 9) // C
                    constants[1] -> result = value // F
                    constants[2] -> result = (value + 459.67) * 5 / 9 // K
                    constants[3] -> result = (value + 459.67) // R
                    constants[4] -> result = (value - 32) * 4 / 9 // Re

                }

                // Кельвин
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value - 273.15) // C
                    constants[1] -> result = (value - 273.15) * 9 / 5 + 32 // F
                    constants[2] -> result = value // K
                    constants[3] -> result = (value * 9 / 5) // R
                    constants[4] -> result = (value - 273.15) * 4 / 5 // Re

                }

                // Ранкин
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = ((value - 491.67) * 5 / 9) // C
                    constants[1] -> result = (value - 459.67) // F
                    constants[2] -> result = (value *  5 / 9) // K
                    constants[3] -> result = value // R
                    constants[4] -> result = (value - 491.67) * 4 / 9 // Re

                }

                // Реомюр
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 5 / 4) // C
                    constants[1] -> result = (value * 9 / 4) + 32 // F
                    constants[2] -> result = (value * 5 / 4) + 273.15 // K
                    constants[3] -> result = (value * 9 / 4 ) + 491.67 // R
                    constants[4] -> result = value // Re

                }

            }

            resultConvert.text = "≈" + String.format("%.2f",result)
        }

    }
}