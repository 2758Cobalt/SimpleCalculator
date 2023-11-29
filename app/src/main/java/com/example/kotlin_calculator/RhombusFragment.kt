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
import kotlin.math.pow
import kotlin.math.sin

class RhombusFragment : Fragment() {
    private lateinit var constants : Array<String>

    private lateinit var rhombusSides : EditText
    private lateinit var rhombusAngle : EditText
    private lateinit var rhombusHeight : EditText
    private lateinit var rhombusDiagonalFirst : EditText
    private lateinit var rhombusDiagonalSecond : EditText

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    private lateinit var dropDown : Spinner

    private lateinit var rhombusResult : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_rhombus, container, false) // Сам view фрагмента


        constants = resources.getStringArray(R.array.rhombus_formulas) // Получение строкового массива

        rhombusResult = view.findViewById(R.id.areaRhombusResult) // Поле результат
        dropDown = view.findViewById(R.id.dropDownFormula) // Формулы

        // Стороны ромба и прочее
        rhombusSides = view.findViewById(R.id.inputRhombusSideA)
        rhombusAngle = view.findViewById(R.id.inputRhombusAngle)
        rhombusHeight = view.findViewById(R.id.inputRhombusHeight)
        
        rhombusDiagonalFirst = view.findViewById(R.id.inputRhombusDiagonalFirst)
        rhombusDiagonalSecond = view.findViewById(R.id.inputRhombusDiagonalSecond)
        
        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton.setOnClickListener { calculateRhombus(it) }
        resetButton.setOnClickListener { resetFieldsRhombus(it) }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rhombus_formulas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Установка строкового массива
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Применение адаптера к спинеру
            dropDown.adapter = adapter
        }
        return view
    }
    fun resetFieldsRhombus(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        rhombusSides.text.clear()
        rhombusHeight.text.clear()
        rhombusAngle.text.clear()

        rhombusDiagonalFirst.text.clear()
        rhombusDiagonalSecond.text.clear()

        rhombusResult.text = "0"
    }

    fun calculateRhombus(view : View){
        var result = 0.0



        when (dropDown.selectedItem.toString()) {
            constants[0] ->
                if(rhombusSides.text.isNotEmpty() && rhombusHeight.text.isNotEmpty()) {
                    val sideA = rhombusSides.text.toString().toDouble()
                    val height = rhombusHeight.text.toString().toDouble()

                    result = sideA * height
            }
            constants[1] -> if(rhombusSides.text.isNotEmpty() && rhombusAngle.text.isNotEmpty()) {
                val sideA = rhombusSides.text.toString().toDouble()
                val sinus = rhombusAngle.text.toString().toDouble()

                result = sideA.pow(2.0) * sin(sinus)
            }

            constants[2] ->
                if(rhombusDiagonalFirst.text.isNotEmpty() && rhombusDiagonalSecond.text.isNotEmpty()) {
                    val diagonalFirst = rhombusDiagonalFirst.text.toString().toDouble()
                    val diagonalSecond = rhombusDiagonalSecond.text.toString().toDouble()

                    result = (diagonalFirst * diagonalSecond) / 2
                }
        }

        rhombusResult.text = " S = ${result}"


    }
}