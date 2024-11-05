package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterNBinding
import com.cobaltumapps.simplecalculator.v15.fragments.converter.enums.ConverterUnit
import com.cobaltumapps.simplecalculator.v15.fragments.converter.interfaces.SelectorFragmentListener

class ConverterUnitFragment(
    private val listener: SelectorFragmentListener? = null
): Fragment(), SelectorFragmentListener {
    private val binding by lazy { FragmentConverterNBinding.inflate(layoutInflater) }
    private var currentlySelectedUnit: ConverterUnit? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.converterUnitTitle.text = currentlySelectedUnit?.name
    }

    override fun onSelectedItem(selectedUnit: ConverterUnit) {
        currentlySelectedUnit = selectedUnit
    }
}