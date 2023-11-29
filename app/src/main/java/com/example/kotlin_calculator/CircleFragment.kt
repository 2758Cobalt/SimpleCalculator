package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.PI
import kotlin.math.pow

class CircleFragment : Fragment() {
    private lateinit var circleRadius: EditText
    private lateinit var circleLength: EditText

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    private lateinit var calculateButtonLength: Button
    private lateinit var resetButtonLength: Button

    private lateinit var circleResult: TextView
    private lateinit var circleLengthResult: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_circle, container, false) // Сам view фрагмента

        circleRadius = view.findViewById(R.id.inputCircleRadius)
        circleLength = view.findViewById(R.id.inputCircleLength)

        circleResult = view.findViewById(R.id.areaCircleResult)
        circleLengthResult = view.findViewById(R.id.areaCircleLengthResult)


        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButtonLength = view.findViewById(R.id.buttonConvertLength)
        resetButtonLength = view.findViewById(R.id.buttonResetLength)

        calculateButton.setOnClickListener { calculateCircle(it) }
        resetButton.setOnClickListener { resetFieldsCircle(it) }

        calculateButtonLength.setOnClickListener { calculateCircleLength(it) }
        resetButtonLength.setOnClickListener { resetFieldsCircleLength(it) }

        return view
    }


    fun resetFieldsCircle(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        circleRadius.text.clear()
        circleResult.text = "0"
    }

    fun calculateCircle(view: View) {
        var result : Double
        if (circleRadius.text.isNotEmpty()) {
            val radius = circleRadius.text.toString().toDouble()
            result = PI * radius.pow(2.0)

            circleResult.text = "S ≈ " + String.format("%.3f", result)
        }
    }

    fun resetFieldsCircleLength(view: View) {
        // Сбрасывает значения в полях - Кнопка "Reset"
        circleLength.text.clear()
        circleLengthResult.text = "0"
    }

    fun calculateCircleLength(view: View) {
        var result: Double
        if (circleLength.text.isNotEmpty()) {
            val radius = circleLength.text.toString().toDouble()
            result = 2 * PI * radius

            circleLengthResult.text = "S ≈ " + String.format("%.3f", result)

        }
    }
}