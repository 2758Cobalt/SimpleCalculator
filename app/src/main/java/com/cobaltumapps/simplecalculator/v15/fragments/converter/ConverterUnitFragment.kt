package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterBinding
import com.cobaltumapps.simplecalculator.v15.converter.enums.ConverterUnit
import com.cobaltumapps.simplecalculator.v15.converter.interfaces.SelectorFragmentListener

class ConverterUnitFragment: Fragment(), SelectorFragmentListener {
    private lateinit var binding: FragmentConverterBinding
    private var currentlySelectedUnit: ConverterUnit? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUnitTitle()
    }

    override fun onSelectedItem(selectedUnit: ConverterUnit) {
        currentlySelectedUnit = selectedUnit
        if (::binding.isInitialized) {
            setUnitTitle()
        }
    }

    private fun setUnitTitle() {
        binding.converterUnitTitle.text = currentlySelectedUnit?.name
    }
}