package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class SelectorFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_selector, container, false) // Сам view фрагмента

        //Ссылки на компоненты
        val unitButtons = arrayOf(
            view.findViewById(R.id.buttonWeight) as ImageButton,
            view.findViewById(R.id.buttonLength) as ImageButton,
            view.findViewById(R.id.buttonSpeed) as ImageButton,
            view.findViewById(R.id.buttonData) as ImageButton,
            view.findViewById(R.id.buttonTemperature) as ImageButton,
            view.findViewById(R.id.buttonVolume) as ImageButton,
            view.findViewById(R.id.buttonArea) as ImageButton,
            view.findViewById(R.id.buttonFrequency) as ImageButton,
            view.findViewById(R.id.buttonFuelConsumption) as ImageButton,
            view.findViewById(R.id.buttonPressure) as ImageButton,
            view.findViewById(R.id.buttonPower) as ImageButton,
            view.findViewById(R.id.buttonEnergy) as ImageButton
        )
        val mathSolidsButtons = arrayOf(
            view.findViewById(R.id.buttonMathParallelepiped) as ImageButton,
            view.findViewById(R.id.buttonMathPyramid) as ImageButton,
            view.findViewById(R.id.buttonMathCylinder) as ImageButton,
            view.findViewById(R.id.buttonMathCone) as ImageButton,
            view.findViewById(R.id.buttonMathSphere) as ImageButton,
            view.findViewById(R.id.buttonMathPrism) as ImageButton,
            view.findViewById(R.id.buttonMathRectangle) as ImageButton,
            view.findViewById(R.id.buttonMathTriangle) as ImageButton,
            view.findViewById(R.id.buttonMathRhombus) as ImageButton,
            view.findViewById(R.id.buttonMathCircle) as ImageButton,
            view.findViewById(R.id.buttonMathTrapezium) as ImageButton
        )

        for ((index, button) in unitButtons.withIndex()) {
            button.setOnClickListener {
                val convertorFrag = ConverterFragment()
                convertorFrag.dataIdSet(index)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, convertorFrag).commit()
            }
        }

        for ((index, button) in mathSolidsButtons.withIndex()) {
            button.setOnClickListener {
                val calculatorAlgebra = MathFigureFragment()
                calculatorAlgebra.dataIdSet(index)
                calculatorAlgebra.imageSet(button.drawable)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, calculatorAlgebra).commit()
            }
        }


        return view
    }
}