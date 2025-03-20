package com.cobaltumapps.simplecalculator.v15.fragments.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cobaltumapps.simplecalculator.databinding.FragmentConverterCalculatorBinding
import com.cobaltumapps.simplecalculator.v15.converter.calculator.ConverterDiagonalMatrixCalculator
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterNumpadController
import com.cobaltumapps.simplecalculator.v15.converter.controllers.ConverterUserInputHandlerListener
import com.cobaltumapps.simplecalculator.v15.fragments.numpad.ConverterNumpadFragment

class ConverterCalculatorFragment: Fragment(), ConverterUserInputHandlerListener {
    private val binding by lazy { FragmentConverterCalculatorBinding.inflate(layoutInflater) }

    private val converterNumpadController = ConverterNumpadController(this@ConverterCalculatorFragment)

    private val converterNumpadFragment = ConverterNumpadFragment(converterNumpadController)

    private val converterDiagonalMatrixCalculator = ConverterDiagonalMatrixCalculator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            parentFragmentManager.commit {
                replace(converterNumpadFrame.id, converterNumpadFragment, ConverterNumpadFragment.FRAG_TAG)
            }

        }
    }

    override fun receiveUserInput(receivedInput: String) {
        binding.converterNumpadUserInput.text = receivedInput
    }

    companion object {
        const val FRAG_TAG = "ConverterCalculatorFragmentTag"
    }
}
