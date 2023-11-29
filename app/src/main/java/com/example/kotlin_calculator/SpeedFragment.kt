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

class SpeedFragment: Fragment() {
    private lateinit var constants : Array<String>

    private lateinit var valueForConvert : EditText

    private lateinit var resultConvert : TextView

    private lateinit var dropdownFrom : Spinner
    private lateinit var dropdownIn : Spinner

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_speed, container, false) // Сам view фрагмента

        constants = resources.getStringArray(R.array.speed_values)

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
            R.array.speed_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Установка строкового массива
            // Применение адаптера к спинеру
            dropdownFrom.adapter = adapter
            dropdownIn.adapter = adapter
        }

        return view
    }

    fun calculateConvertation(view : View) {
        // Расчёт скорости
        if(valueForConvert.text.isNotEmpty()) // Проверяет не пустое ли поле ввода
        {
            val value = valueForConvert.text.toString().toDouble()
            var result = 0.0
            when (dropdownFrom.selectedItem.toString()) {

                // скорость света
                constants[0] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1) // c=299 792 458
                    constants[1] -> result = (value * 299792458) // m\s
                    constants[2] -> result = (value * 1079252848.8) // km/h
                    constants[3] -> result = (value * 299792.458) // km/s
                    constants[4] -> result = (value * 670616629.3844) // mph/h
                    constants[5] -> result = (value * 186282.39705122) // mps
                    constants[6] -> result = (value * 582749918.36357) // Kn
                }

                // м/с метры в секунду
                constants[1] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 3.3356000000000) // c=299 792 458
                    constants[1] -> result = (value * 1) // m\s
                    constants[2] -> result = (value * 3.6) // km/h
                    constants[3] -> result = (value * 1000) // km/s
                    constants[4] -> result = (value * 2.23694) // mph/h
                    constants[5] -> result = (value * 1.94384) // mps
                    constants[6] -> result = (value * 3.28084) // Kn
                }

                // км/ч - километры в час
                constants[2] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 9.2656693110598) // c=299 792 458
                    constants[1] -> result = (value * 0.277778) // m\s
                    constants[2] -> result = (value * 1) // km/h
                    constants[3] -> result = (value * 0.000277778) // km/s
                    constants[4] -> result = (value * 0.621371) // mph/h
                    constants[5] -> result = (value * 0.539957) // mps
                    constants[6] -> result = (value * 0.911344) // Kn
                }

                // км/с километры в секунду
                constants[3] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 3.3356409519815) // c=299 792 458
                    constants[1] -> result = (value * 1000) // m\s
                    constants[2] -> result = (value * 3600) // km/h
                    constants[3] -> result = (value * 1) // km/s
                    constants[4] -> result = (value * 2236.9362920544) // mph/h
                    constants[5] -> result = (value * 1943.8444924574) // mps
                    constants[6] -> result = (value * 3280.84) // Kn
                }

                // м/ч миля в час
                constants[4] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1.4911649311738) // c=299 792 458
                    constants[1] -> result = (value * 0.44704) // m\s
                    constants[2] -> result = (value * 1.609344) // km/h
                    constants[3] -> result = (value * 0.00044704) // km/s
                    constants[4] -> result = (value * 1) // mph/h
                    constants[5] -> result = (value * 0.00027) // mps
                    constants[6] -> result = (value * 0.868976) // Kn
                }

                // мл/с миля в секунду
                constants[5] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 5.3681937522257) // c=299 792 458
                    constants[1] -> result = (value * 83333) // m\s
                    constants[2] -> result = (value * 5793.6384) // km/h
                    constants[3] -> result = (value * 1.609344) // km/s
                    constants[4] -> result = (value * 3600) // mph/h
                    constants[5] -> result = (value * 1) // mps
                    constants[6] -> result = (value * 3128.31447087) // Kn
                }

                // уз - узлы
                constants[6] -> when (dropdownIn.selectedItem.toString()) {
                    constants[0] -> result = (value * 1.7160019563934) // c=299 792 458
                    constants[1] -> result = (value * 0.5144) // m\s
                    constants[2] -> result = (value * 1.851999999984) // km/h
                    constants[3] -> result = (value * 0.000514) // km/s
                    constants[4] -> result = (value * 1.1507794480136) // mph/h
                    constants[5] -> result = (value * 0.00031966095778156) // mps
                    constants[6] -> result = (value * 1) // Kn
                }
            }
            resultConvert.text = "≈" + result.toString()
        }
    }

    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        resultConvert.text ="0"
        valueForConvert.setText("0")
    }

}