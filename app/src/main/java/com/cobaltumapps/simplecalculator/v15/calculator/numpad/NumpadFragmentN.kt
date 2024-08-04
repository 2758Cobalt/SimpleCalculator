package com.cobaltumapps.simplecalculator.v15.calculator.numpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentNumpadNBinding

// Этот класс является хостом и хранит холдеры (place holders) для других модулей калькулятора
class NumpadFragmentN: Fragment() {
    private lateinit var binding: FragmentNumpadNBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNumpadNBinding.inflate(layoutInflater)
        return binding.root
    }

}