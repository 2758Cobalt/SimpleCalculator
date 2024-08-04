package com.cobaltumapps.simplecalculator.v15.calculator.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentDisplayNBinding

class DisplayFragmentN: Fragment() {
    private lateinit var binding: FragmentDisplayNBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDisplayNBinding.inflate(layoutInflater)
        return binding.root
    }
}