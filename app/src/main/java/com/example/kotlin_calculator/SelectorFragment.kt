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
            view.findViewById(R.id.buttonFrequency) as ImageButton,
            view.findViewById(R.id.buttonFuelConsumption) as ImageButton
        )

        for ((index, button) in unitButtons.withIndex()) {
            button.setOnClickListener {
                val convertorFrag = ConvertorFragment()
                convertorFrag.dataIdSet(index)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container_selector, convertorFrag).commit()
            }
        }


        return view
    }
}