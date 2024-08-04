package com.cobaltumapps.simplecalculator.v15.calculator.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentCalculatorNBinding
import com.cobaltumapps.simplecalculator.v15.calculator.display.DisplayFragmentN

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class CalculatorFragmentN: Fragment() {
    private val display = DisplayFragmentN()

    private lateinit var binding: FragmentCalculatorNBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorNBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction().replace(binding.displayHolder.id, display).commit()
    }
}