package com.example.kotlin_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class SelectorFragment: Fragment() {
    private lateinit var buttonWeight: ImageButton
    private lateinit var buttonLength: ImageButton
    private lateinit var buttonSpeed: ImageButton
    private lateinit var buttonData: ImageButton
    private lateinit var buttonTemperature: ImageButton
    private lateinit var buttonSquare: ImageButton

    private lateinit var buttonParallelepiped: ImageButton
    private lateinit var buttonTriangle: ImageButton
    private lateinit var buttonRhombus: ImageButton
    private lateinit var buttonCircle: ImageButton
    private lateinit var buttonTrapezoid: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_selector, container, false) // Сам view фрагмента

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

//        //Слушатели нажатий
        buttonWeight.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, WeightFragment()).commit() }
        buttonLength.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, LengthFragment()).commit() }
        buttonSpeed.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, SpeedFragment()).commit() }
        buttonData.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, DataFragment()).commit() }
        buttonTemperature.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, TemperatureFragment()).commit() }
        buttonSquare.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, SquareFragment()).commit() }

        buttonParallelepiped.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, ParallelepipedFragment()).commit() }
        buttonTriangle.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, TriangleFragment()).commit() }
        buttonRhombus.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, RhombusFragment()).commit() }
        buttonCircle.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, CircleFragment()).commit() }
        buttonTrapezoid.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, TrapezoidFragment()).commit() }

        return view
    }
}