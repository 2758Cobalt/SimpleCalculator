package com.example.kotlin_calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class ParallelepipedFragment : Fragment() {


    private lateinit var squareSideA : EditText
    private lateinit var squareSideB : EditText
    private lateinit var squareSideC : EditText

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button

    private lateinit var squareResult : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_parallelepiped, container, false) // Сам view фрагмента

        squareSideA = view.findViewById(R.id.inputParallelepipedSideA)
        squareSideB = view.findViewById(R.id.inputParallelepipedSideB)
        squareSideC = view.findViewById(R.id.inputParallelepipedSideC)

        squareResult = view.findViewById(R.id.areaParallelepipedResult)

        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton = view.findViewById(R.id.buttonConvert)
        resetButton = view.findViewById(R.id.buttonReset)

        calculateButton.setOnClickListener { calculateParallelepiped(it) }
        resetButton.setOnClickListener { resetFields(it) }

        return view
    }
    fun resetFields(view : View){
        // Сбрасывает значения в полях - Кнопка "Reset"
        squareSideA.text.clear()
        squareSideB.text.clear()
        squareSideC.text.clear()
        squareResult.text = "0"
    }
    fun calculateParallelepiped(view : View) {
        var result : Double
        if (squareSideA.text.isNotEmpty() && squareSideB.text.isNotEmpty()) {
            if (squareSideC.text.isEmpty() || squareSideC.text.toString() == "0")
                squareSideC.setText("1")

            val sideA = squareSideA.text.toString().toDouble()
            val sideB = squareSideB.text.toString().toDouble()
            val sideC = squareSideC.text.toString().toDouble()

            Log.i("InfoApp","worked")


            result = 2 * ((sideA * sideB) + (sideA * sideC) + (sideB * sideC)) // 2 (ab + ac + bc)
            squareResult.text = "S = " + String.format("%.2f", result)

        }
    }
}