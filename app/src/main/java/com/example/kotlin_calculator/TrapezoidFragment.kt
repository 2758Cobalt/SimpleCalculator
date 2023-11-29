package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class TrapezoidFragment : Fragment() {

    private lateinit var trapezoidSideA: EditText
    private lateinit var trapezoidSideB: EditText
    private lateinit var trapezoidHeight: EditText

    private lateinit var trapezoidResult: TextView

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_trapezoid, container, false) // Сам view фрагмента

        trapezoidSideA = view.findViewById(R.id.inputTrapeziodSideA)
        trapezoidSideB = view.findViewById(R.id.inputTrapeziodSideB)
        trapezoidHeight = view.findViewById(R.id.inputTrapeziodHeight)

        trapezoidResult = view.findViewById(R.id.areaTrapezoidResult)

        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton.setOnClickListener { calculateTrapezoid(it) }
        resetButton.setOnClickListener { resetFieldsTrapezoid(it) }

        return view
    }

    fun resetFieldsTrapezoid(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        trapezoidSideA.text.clear()
        trapezoidResult.text = "0"
    }

    fun calculateTrapezoid(view: View) {
        var result : Double
        if (trapezoidSideA.text.isNotEmpty()) {
            val sideA = trapezoidSideA.text.toString().toDouble()
            val sideB = trapezoidSideB.text.toString().toDouble()
            val height = trapezoidHeight.text.toString().toDouble()

            result = ((sideA + sideB) * height) / 2


            trapezoidResult.text = "S ≈ " + String.format("%.3f", result)
        }
    }

}