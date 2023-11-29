package com.example.kotlin_calculator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ConvertersFragment: Fragment() {
    private lateinit var buttonWeight: Button
    private lateinit var buttonLength: Button
    private lateinit var buttonSpeed: Button
    private lateinit var buttonData: Button
    private lateinit var buttonTemperature: Button
    private lateinit var buttonSquare: Button

    private lateinit var buttonParallelepiped: Button
    private lateinit var buttonTriangle: Button
    private lateinit var buttonRhombus: Button
    private lateinit var buttonCircle: Button
    private lateinit var buttonTrapezoid: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_converters, container, false) // Сам view фрагмента

        //Ссылки на компоненты
        buttonWeight = view.findViewById(R.id.buttonWeight)
        buttonLength = view.findViewById(R.id.buttonLength)
        buttonSpeed = view.findViewById(R.id.buttonSpeed)
        buttonData = view.findViewById(R.id.buttonData)
        buttonTemperature = view.findViewById(R.id.buttonTemperature)
        buttonSquare = view.findViewById(R.id.buttonSquare)

        buttonParallelepiped = view.findViewById(R.id.buttonParallelepiped)
        buttonTriangle = view.findViewById(R.id.buttonTriangle)
        buttonRhombus = view.findViewById(R.id.buttonRhombus)
        buttonCircle = view.findViewById(R.id.buttonCircle)
        buttonTrapezoid = view.findViewById(R.id.buttonTrapezoid)

        //Слушатели нажатий
        buttonWeight.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, WeightFragment()).commit() }
        buttonLength.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, LengthFragment()).commit() }
        buttonSpeed.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, SpeedFragment()).commit() }
        buttonData.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, DataFragment()).commit() }
        buttonTemperature.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, TemperatureFragment()).commit() }
        buttonSquare.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, SquareFragment()).commit() }

        buttonParallelepiped.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ParallelepipedFragment()).commit() }
        buttonTriangle.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, TriangleFragment()).commit() }
        buttonRhombus.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, RhombusFragment()).commit() }
        buttonCircle.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CircleFragment()).commit() }
        buttonTrapezoid.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container, TrapezoidFragment()).commit() }

        return view
    }
}