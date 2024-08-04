package com.cobaltumapps.simplecalculator.v15.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN: Fragment() {
    private lateinit var binding: FragmentCalculatorNBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorNBinding.inflate(layoutInflater)
        return binding.root
    }
}