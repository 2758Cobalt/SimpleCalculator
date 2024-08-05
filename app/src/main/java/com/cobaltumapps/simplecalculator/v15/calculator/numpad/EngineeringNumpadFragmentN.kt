package com.cobaltumapps.simplecalculator.v15.calculator.numpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentEngineeringNBinding


// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class EngineeringNumpadFragmentN: Fragment() {
    private lateinit var binding: FragmentEngineeringNBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEngineeringNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}