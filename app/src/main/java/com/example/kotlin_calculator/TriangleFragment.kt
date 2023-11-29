package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.sqrt

class TriangleFragment : Fragment() {
    private lateinit var triangle_sideA : EditText
    private lateinit var triangle_sideB : EditText
    private lateinit var triangle_sideC : EditText

    private lateinit var triangleResult : TextView

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_triangle, container, false) // Сам view фрагмента


        triangleResult = view.findViewById(R.id.areaTriangleResult)

        // Треугольник
        triangle_sideA = view.findViewById(R.id.inputTriangleSideA)
        triangle_sideB = view.findViewById(R.id.inputTriangleSideB)
        triangle_sideC = view.findViewById(R.id.inputTriangleSideC)

        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton.setOnClickListener { calculateTriangle(it) }
        resetButton.setOnClickListener { resetFieldsTriangle(it) }

        return view
    }

    fun resetFieldsTriangle(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        triangle_sideA.text.clear()
        triangle_sideB.text.clear()
        triangle_sideC.text.clear()
        triangleResult.text = "0"
    }

    fun calculateTriangle(view : View){

        if(triangle_sideA.text.isNotEmpty() && triangle_sideB.text.isNotEmpty() && triangle_sideC.text.isNotEmpty())
        {
            val sideA = triangle_sideA.text.toString().toDouble()
            val sideB = triangle_sideB.text.toString().toDouble()
            val sideC = triangle_sideC.text.toString().toDouble()

            val perimeter = (sideA + sideB + sideC) / 2

            triangleResult.text = "≈" + String.format("%.2f",
                sqrt((perimeter) * (perimeter - sideA) * (perimeter - sideB) * (perimeter - sideC))
            )
        }

    }
}